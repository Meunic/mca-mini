import React, {useState, useEffect, useCallback} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  RefreshControl,
  TouchableOpacity,
  Alert,
} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';
import {useAuth} from '../context/AuthContext';
import {useFocusEffect} from '@react-navigation/native';
import api from '../config/api';
import Card from '../components/Card';
import {formatCurrency} from '../utils/currency';

const DashboardScreen = ({navigation}) => {
  const {logout} = useAuth();
  const [loading, setLoading] = useState(false);
  const [walletBalance, setWalletBalance] = useState(0);
  const [transactions, setTransactions] = useState([]);
  const [totalIncome, setTotalIncome] = useState(0);
  const [totalExpense, setTotalExpense] = useState(0);

  React.useLayoutEffect(() => {
    navigation.setOptions({
      headerRight: () => (
        <View style={styles.headerButtons}>
          <TouchableOpacity
            onPress={() => navigation.navigate('Analytics')}
            style={styles.headerButton}>
            <Icon name="analytics" size={24} color="#fff" />
          </TouchableOpacity>
          <TouchableOpacity
            onPress={() => navigation.navigate('Categories')}
            style={styles.headerButton}>
            <Icon name="category" size={24} color="#fff" />
          </TouchableOpacity>
          <TouchableOpacity
            onPress={() => navigation.navigate('Budgets')}
            style={styles.headerButton}>
            <Icon name="account-balance" size={24} color="#fff" />
          </TouchableOpacity>
          <TouchableOpacity
            onPress={handleLogout}
            style={styles.headerButton}>
            <Icon name="logout" size={24} color="#fff" />
          </TouchableOpacity>
        </View>
      ),
    });
  }, [navigation]);

  useFocusEffect(
    useCallback(() => {
      loadData();
    }, []),
  );

  const loadData = async () => {
    setLoading(true);
    try {
      const [walletRes, transactionsRes] = await Promise.all([
        api.get('/wallet'),
        api.get('/transactions'),
      ]);

      setWalletBalance(walletRes.data.wallet_balance);
      const txns = transactionsRes.data;
      setTransactions(txns);

      const income = txns
        .filter(t => t.type === 'income')
        .reduce((sum, t) => sum + t.amount, 0);
      const expense = txns
        .filter(t => t.type === 'expense')
        .reduce((sum, t) => sum + t.amount, 0);

      setTotalIncome(income);
      setTotalExpense(expense);
    } catch (error) {
      Alert.alert('Error', 'Failed to load data');
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    Alert.alert('Logout', 'Are you sure you want to logout?', [
      {text: 'Cancel', style: 'cancel'},
      {text: 'Logout', onPress: logout, style: 'destructive'},
    ]);
  };

  const deleteTransaction = async id => {
    try {
      await api.delete(`/transactions/${id}`);
      loadData();
    } catch (error) {
      Alert.alert('Error', 'Failed to delete transaction');
    }
  };

  return (
    <View style={styles.container}>
      <ScrollView
        style={styles.scrollView}
        refreshControl={
          <RefreshControl refreshing={loading} onRefresh={loadData} />
        }>
        {/* Wallet Card */}
        <Card style={styles.walletCard}>
          <View style={styles.walletHeader}>
            <Text style={styles.walletLabel}>Wallet Balance</Text>
            <Icon name="account-balance-wallet" size={24} color="#fff" />
          </View>
          <Text style={styles.walletAmount}>{formatCurrency(walletBalance)}</Text>
        </Card>

        {/* Overview Cards */}
        <View style={styles.overviewRow}>
          <Card style={[styles.overviewCard, styles.incomeCard]}>
            <View style={styles.overviewHeader}>
              <Text style={styles.overviewLabel}>Income</Text>
              <Icon name="trending-up" size={18} color="#10B981" />
            </View>
            <Text style={[styles.overviewAmount, {color: '#10B981'}]}>
              {formatCurrency(totalIncome)}
            </Text>
          </Card>

          <Card style={[styles.overviewCard, styles.expenseCard]}>
            <View style={styles.overviewHeader}>
              <Text style={styles.overviewLabel}>Expense</Text>
              <Icon name="trending-down" size={18} color="#EF4444" />
            </View>
            <Text style={[styles.overviewAmount, {color: '#EF4444'}]}>
              {formatCurrency(totalExpense)}
            </Text>
          </Card>
        </View>

        {/* Transactions */}
        <Text style={styles.sectionTitle}>Recent Transactions</Text>
        
        {transactions.length === 0 ? (
          <Card>
            <View style={styles.emptyState}>
              <Icon name="receipt-long" size={48} color="#9CA3AF" />
              <Text style={styles.emptyText}>No transactions yet</Text>
              <Text style={styles.emptySubtext}>
                Start tracking your expenses by adding your first transaction
              </Text>
            </View>
          </Card>
        ) : (
          transactions.slice(0, 10).map(transaction => (
            <Card key={transaction.id} style={styles.transactionCard}>
              <View style={styles.transactionRow}>
                <View style={styles.transactionLeft}>
                  <View
                    style={[
                      styles.transactionIcon,
                      transaction.type === 'expense'
                        ? styles.expenseIcon
                        : styles.incomeIcon,
                    ]}>
                    <Icon
                      name={transaction.type === 'expense' ? 'shopping-cart' : 'attach-money'}
                      size={20}
                      color={transaction.type === 'expense' ? '#EF4444' : '#10B981'}
                    />
                  </View>
                  <View>
                    <Text style={styles.transactionCategory}>
                      {transaction.category}
                    </Text>
                    {transaction.note && (
                      <Text style={styles.transactionNote} numberOfLines={1}>
                        {transaction.note}
                      </Text>
                    )}
                    <Text style={styles.transactionDate}>
                      {transaction.date} • {transaction.method}
                    </Text>
                  </View>
                </View>
                <View style={styles.transactionRight}>
                  <Text
                    style={[
                      styles.transactionAmount,
                      transaction.type === 'expense'
                        ? styles.expenseAmount
                        : styles.incomeAmount,
                    ]}>
                    {transaction.type === 'expense' ? '-' : '+'}
                    {formatCurrency(transaction.amount)}
                  </Text>
                  <TouchableOpacity
                    onPress={() => {
                      Alert.alert(
                        'Delete Transaction',
                        'Are you sure?',
                        [
                          {text: 'Cancel', style: 'cancel'},
                          {
                            text: 'Delete',
                            onPress: () => deleteTransaction(transaction.id),
                            style: 'destructive',
                          },
                        ],
                      );
                    }}>
                    <Icon name="delete" size={20} color="#EF4444" />
                  </TouchableOpacity>
                </View>
              </View>
            </Card>
          ))
        )}
      </ScrollView>

      {/* FAB */}
      <TouchableOpacity
        style={styles.fab}
        onPress={() => navigation.navigate('AddExpense')}
        activeOpacity={0.8}>
        <Icon name="add" size={28} color="#fff" />
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F3F4F6',
  },
  scrollView: {
    flex: 1,
    padding: 16,
  },
  headerButtons: {
    flexDirection: 'row',
    marginRight: 8,
  },
  headerButton: {
    marginLeft: 16,
  },
  walletCard: {
    backgroundColor: '#8B5CF6',
    marginBottom: 16,
  },
  walletHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 12,
  },
  walletLabel: {
    color: 'rgba(255,255,255,0.9)',
    fontSize: 16,
  },
  walletAmount: {
    color: '#fff',
    fontSize: 32,
    fontWeight: 'bold',
  },
  overviewRow: {
    flexDirection: 'row',
    marginBottom: 16,
    gap: 12,
  },
  overviewCard: {
    flex: 1,
  },
  overviewHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 8,
  },
  overviewLabel: {
    fontSize: 14,
    color: '#6B7280',
  },
  overviewAmount: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  sectionTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: '#111827',
    marginBottom: 12,
  },
  transactionCard: {
    marginBottom: 12,
  },
  transactionRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  transactionLeft: {
    flexDirection: 'row',
    alignItems: 'center',
    flex: 1,
  },
  transactionIcon: {
    width: 40,
    height: 40,
    borderRadius: 8,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 12,
  },
  expenseIcon: {
    backgroundColor: 'rgba(239, 68, 68, 0.1)',
  },
  incomeIcon: {
    backgroundColor: 'rgba(16, 185, 129, 0.1)',
  },
  transactionCategory: {
    fontSize: 16,
    fontWeight: '600',
    color: '#111827',
  },
  transactionNote: {
    fontSize: 12,
    color: '#6B7280',
    marginTop: 2,
  },
  transactionDate: {
    fontSize: 11,
    color: '#9CA3AF',
    marginTop: 2,
  },
  transactionRight: {
    alignItems: 'flex-end',
  },
  transactionAmount: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
  },
  expenseAmount: {
    color: '#EF4444',
  },
  incomeAmount: {
    color: '#10B981',
  },
  emptyState: {
    alignItems: 'center',
    padding: 32,
  },
  emptyText: {
    fontSize: 18,
    fontWeight: '600',
    color: '#111827',
    marginTop: 16,
  },
  emptySubtext: {
    fontSize: 14,
    color: '#6B7280',
    textAlign: 'center',
    marginTop: 8,
  },
  fab: {
    position: 'absolute',
    right: 16,
    bottom: 16,
    width: 56,
    height: 56,
    borderRadius: 28,
    backgroundColor: '#8B5CF6',
    justifyContent: 'center',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 4},
    shadowOpacity: 0.3,
    shadowRadius: 4,
    elevation: 8,
  },
});

export default DashboardScreen;
