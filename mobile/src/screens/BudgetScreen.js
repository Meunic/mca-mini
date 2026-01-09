import React, {useState, useEffect} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
  TouchableOpacity,
} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';
import {Picker} from '@react-native-picker/picker';
import api from '../config/api';
import Button from '../components/Button';
import Input from '../components/Input';
import Card from '../components/Card';
import {formatCurrency} from '../utils/currency';

const BudgetScreen = () => {
  const [budgets, setBudgets] = useState([]);
  const [categories, setCategories] = useState([]);
  const [category, setCategory] = useState('');
  const [amount, setAmount] = useState('');
  const [period, setPeriod] = useState('monthly');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const [budgetsRes, categoriesRes] = await Promise.all([
        api.get('/budgets'),
        api.get('/categories'),
      ]);
      setBudgets(budgetsRes.data);
      setCategories(categoriesRes.data);
      if (categoriesRes.data.length > 0) {
        setCategory(categoriesRes.data[0].name);
      }
    } catch (error) {
      Alert.alert('Error', 'Failed to load data');
    }
  };

  const addBudget = async () => {
    if (!amount || parseFloat(amount) <= 0) return;

    setLoading(true);
    try {
      await api.post('/budgets', {
        category,
        amount: parseFloat(amount),
        period,
      });
      setAmount('');
      loadData();
    } catch (error) {
      Alert.alert('Error', 'Failed to add budget');
    } finally {
      setLoading(false);
    }
  };

  const deleteBudget = id => {
    Alert.alert('Delete Budget', 'Are you sure?', [
      {text: 'Cancel', style: 'cancel'},
      {
        text: 'Delete',
        onPress: async () => {
          try {
            await api.delete(`/budgets/${id}`);
            loadData();
          } catch (error) {
            Alert.alert('Error', 'Failed to delete budget');
          }
        },
        style: 'destructive',
      },
    ]);
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.content}>
        <Card style={styles.addCard}>
          <Text style={styles.title}>Set New Budget</Text>
          
          <View style={styles.pickerGroup}>
            <Text style={styles.label}>Category</Text>
            <View style={styles.pickerContainer}>
              <Picker selectedValue={category} onValueChange={setCategory}>
                {categories.map(cat => (
                  <Picker.Item key={cat.id} label={cat.name} value={cat.name} />
                ))}
              </Picker>
            </View>
          </View>

          <View style={styles.row}>
            <View style={{flex: 1}}>
              <Input
                label="Amount (₹)"
                value={amount}
                onChangeText={setAmount}
                placeholder="0"
                keyboardType="numeric"
              />
            </View>
            <View style={{flex: 1}}>
              <Text style={styles.label}>Period</Text>
              <View style={styles.pickerContainer}>
                <Picker selectedValue={period} onValueChange={setPeriod}>
                  <Picker.Item label="Monthly" value="monthly" />
                  <Picker.Item label="Weekly" value="weekly" />
                </Picker>
              </View>
            </View>
          </View>

          <Button title="Set Budget" onPress={addBudget} loading={loading} />
        </Card>

        <Text style={styles.sectionTitle}>
          Active Budgets ({budgets.length})
        </Text>

        {budgets.length === 0 ? (
          <Card>
            <View style={styles.emptyState}>
              <Icon name="account-balance" size={48} color="#9CA3AF" />
              <Text style={styles.emptyText}>No budgets set</Text>
            </View>
          </Card>
        ) : (
          budgets.map(budget => (
            <Card key={budget.id} style={styles.budgetCard}>
              <View style={styles.budgetHeader}>
                <View>
                  <Text style={styles.budgetCategory}>{budget.category}</Text>
                  <Text style={styles.budgetPeriod}>
                    {budget.period.charAt(0).toUpperCase() + budget.period.slice(1)}
                  </Text>
                </View>
                <TouchableOpacity onPress={() => deleteBudget(budget.id)}>
                  <Icon name="delete" size={24} color="#EF4444" />
                </TouchableOpacity>
              </View>
              <Text style={styles.budgetAmount}>
                Budget: {formatCurrency(budget.amount)}
              </Text>
            </Card>
          ))
        )}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F3F4F6',
  },
  content: {
    padding: 16,
  },
  addCard: {
    backgroundColor: '#EDE9FE',
    marginBottom: 16,
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 16,
  },
  pickerGroup: {
    marginBottom: 16,
  },
  label: {
    fontSize: 14,
    fontWeight: '600',
    color: '#374151',
    marginBottom: 8,
  },
  pickerContainer: {
    borderWidth: 1,
    borderColor: '#D1D5DB',
    borderRadius: 12,
    backgroundColor: '#fff',
  },
  row: {
    flexDirection: 'row',
    gap: 12,
    marginBottom: 16,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 12,
  },
  budgetCard: {
    marginBottom: 12,
  },
  budgetHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: 12,
  },
  budgetCategory: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  budgetPeriod: {
    fontSize: 12,
    color: '#6B7280',
  },
  budgetAmount: {
    fontSize: 16,
    color: '#8B5CF6',
    fontWeight: '600',
  },
  emptyState: {
    alignItems: 'center',
    padding: 32,
  },
  emptyText: {
    fontSize: 16,
    color: '#6B7280',
    marginTop: 12,
  },
});

export default BudgetScreen;
