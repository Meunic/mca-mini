import React, {useState, useEffect} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Dimensions,
} from 'react-native';
import {LineChart, BarChart} from 'react-native-chart-kit';
import Icon from 'react-native-vector-icons/MaterialIcons';
import api from '../config/api';
import Card from '../components/Card';
import {formatCurrency} from '../utils/currency';

const screenWidth = Dimensions.get('window').width - 32;

const AnalyticsScreen = () => {
  const [transactions, setTransactions] = useState([]);
  const [totalIncome, setTotalIncome] = useState(0);
  const [totalExpense, setTotalExpense] = useState(0);
  const [categoryBreakdown, setCategoryBreakdown] = useState([]);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const response = await api.get('/transactions');
      const txns = response.data;
      setTransactions(txns);

      const income = txns
        .filter(t => t.type === 'income')
        .reduce((sum, t) => sum + t.amount, 0);
      const expense = txns
        .filter(t => t.type === 'expense')
        .reduce((sum, t) => sum + t.amount, 0);

      setTotalIncome(income);
      setTotalExpense(expense);

      // Category breakdown
      const categories = txns
        .filter(t => t.type === 'expense')
        .reduce((acc, t) => {
          acc[t.category] = (acc[t.category] || 0) + t.amount;
          return acc;
        }, {});

      const breakdown = Object.entries(categories)
        .map(([category, amount]) => ({
          category,
          amount,
          percentage: (amount / expense) * 100,
        }))
        .sort((a, b) => b.amount - a.amount);

      setCategoryBreakdown(breakdown);
    } catch (error) {
      console.error('Failed to load analytics', error);
    }
  };

  const netSavings = totalIncome - totalExpense;

  // Chart data
  const chartData = {
    labels: categoryBreakdown.slice(0, 5).map(c => c.category.slice(0, 4)),
    datasets: [
      {
        data: categoryBreakdown.slice(0, 5).map(c => c.amount),
      },
    ],
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.content}>
        {/* Summary Cards */}
        <View style={styles.row}>
          <Card style={[styles.summaryCard, {backgroundColor: '#DCFCE7'}]}>
            <Icon name="trending-up" size={24} color="#10B981" />
            <Text style={styles.summaryLabel}>Income</Text>
            <Text style={[styles.summaryAmount, {color: '#10B981'}]}>
              {formatCurrency(totalIncome)}
            </Text>
          </Card>
          <Card style={[styles.summaryCard, {backgroundColor: '#FEE2E2'}]}>
            <Icon name="trending-down" size={24} color="#EF4444" />
            <Text style={styles.summaryLabel}>Expense</Text>
            <Text style={[styles.summaryAmount, {color: '#EF4444'}]}>
              {formatCurrency(totalExpense)}
            </Text>
          </Card>
        </View>

        {/* Net Savings */}
        <Card
          style={[
            styles.savingsCard,
            {backgroundColor: netSavings >= 0 ? '#DCFCE7' : '#FEE2E2'},
          ]}>
          <View style={styles.savingsRow}>
            <View>
              <Text style={styles.savingsLabel}>Net Savings</Text>
              <Text
                style={[
                  styles.savingsAmount,
                  {color: netSavings >= 0 ? '#10B981' : '#EF4444'},
                ]}>
                {formatCurrency(netSavings)}
              </Text>
            </View>
            <Icon
              name={netSavings >= 0 ? 'savings' : 'money-off'}
              size={40}
              color={netSavings >= 0 ? '#10B981' : '#EF4444'}
            />
          </View>
        </Card>

        {/* Chart */}
        {categoryBreakdown.length > 0 && (
          <Card>
            <Text style={styles.chartTitle}>Top 5 Categories</Text>
            <BarChart
              data={chartData}
              width={screenWidth - 32}
              height={220}
              chartConfig={{
                backgroundColor: '#fff',
                backgroundGradientFrom: '#fff',
                backgroundGradientTo: '#fff',
                decimalPlaces: 0,
                color: (opacity = 1) => `rgba(139, 92, 246, ${opacity})`,
                labelColor: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,
              }}
              style={styles.chart}
            />
          </Card>
        )}

        {/* Category Breakdown */}
        <Text style={styles.sectionTitle}>Category Breakdown</Text>
        {categoryBreakdown.map((item, index) => (
          <Card key={index} style={styles.categoryCard}>
            <View style={styles.categoryRow}>
              <View style={styles.categoryLeft}>
                <View style={styles.iconContainer}>
                  <Icon name="category" size={20} color="#8B5CF6" />
                </View>
                <View>
                  <Text style={styles.categoryName}>{item.category}</Text>
                  <Text style={styles.categoryPercentage}>
                    {item.percentage.toFixed(1)}%
                  </Text>
                </View>
              </View>
              <Text style={styles.categoryAmount}>
                {formatCurrency(item.amount)}
              </Text>
            </View>
            <View style={styles.progressBar}>
              <View
                style={[
                  styles.progressFill,
                  {width: `${item.percentage}%`},
                ]}
              />
            </View>
          </Card>
        ))}
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
  row: {
    flexDirection: 'row',
    gap: 12,
    marginBottom: 16,
  },
  summaryCard: {
    flex: 1,
    alignItems: 'center',
  },
  summaryLabel: {
    fontSize: 12,
    color: '#6B7280',
    marginTop: 8,
  },
  summaryAmount: {
    fontSize: 18,
    fontWeight: 'bold',
    marginTop: 4,
  },
  savingsCard: {
    marginBottom: 16,
  },
  savingsRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  savingsLabel: {
    fontSize: 14,
    color: '#6B7280',
  },
  savingsAmount: {
    fontSize: 24,
    fontWeight: 'bold',
    marginTop: 4,
  },
  chartTitle: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 12,
  },
  chart: {
    marginVertical: 8,
    borderRadius: 16,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 12,
  },
  categoryCard: {
    marginBottom: 12,
  },
  categoryRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 8,
  },
  categoryLeft: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
  },
  iconContainer: {
    width: 40,
    height: 40,
    borderRadius: 8,
    backgroundColor: '#EDE9FE',
    justifyContent: 'center',
    alignItems: 'center',
  },
  categoryName: {
    fontSize: 16,
    fontWeight: '600',
  },
  categoryPercentage: {
    fontSize: 12,
    color: '#8B5CF6',
  },
  categoryAmount: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  progressBar: {
    height: 6,
    backgroundColor: '#E5E7EB',
    borderRadius: 3,
    overflow: 'hidden',
  },
  progressFill: {
    height: '100%',
    backgroundColor: '#8B5CF6',
  },
});

export default AnalyticsScreen;
