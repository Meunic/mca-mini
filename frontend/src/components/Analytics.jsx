import React, { useMemo } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { PieChart, Pie, Cell, ResponsiveContainer, LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import { formatCurrency } from '@/utils/currency';

function Analytics({ expenses, categories }) {
  const categoryData = useMemo(() => {
    const categorySpending = {};
    const expenseOnly = expenses.filter(exp => exp.type !== 'income');
    
    expenseOnly.forEach(exp => {
      if (!categorySpending[exp.category]) {
        categorySpending[exp.category] = 0;
      }
      categorySpending[exp.category] += exp.amount;
    });

    return Object.entries(categorySpending).map(([name, value]) => {
      const category = categories.find(c => c.name === name);
      return {
        name,
        value: parseFloat(value.toFixed(2)),
        color: category?.color || '#667eea'
      };
    });
  }, [expenses, categories]);

  const monthlyData = useMemo(() => {
    const monthlyStats = {};
    expenses.forEach(exp => {
      const month = exp.date.substring(0, 7); // YYYY-MM
      if (!monthlyStats[month]) {
        monthlyStats[month] = { income: 0, expense: 0 };
      }
      
      if (exp.type === 'income') {
        monthlyStats[month].income += exp.amount;
      } else {
        monthlyStats[month].expense += exp.amount;
      }
    });

    return Object.entries(monthlyStats)
      .sort(([a], [b]) => a.localeCompare(b))
      .map(([month, stats]) => ({
        month,
        income: parseFloat(stats.income.toFixed(2)),
        expense: parseFloat(stats.expense.toFixed(2))
      }));
  }, [expenses]);

  if (expenses.length === 0) {
    return (
      <Card data-testid="analytics-empty">
        <CardContent className="py-12 text-center">
          <p className="text-gray-500">No data to display. Start adding expenses to see analytics!</p>
        </CardContent>
      </Card>
    );
  }

  return (
    <div className="space-y-6" data-testid="analytics-section">
      <div className="grid md:grid-cols-2 gap-6">
        {/* Pie Chart - Spending by Category */}
        <Card>
          <CardHeader>
            <CardTitle>Spending by Category</CardTitle>
          </CardHeader>
          <CardContent>
            {categoryData.length > 0 ? (
              <ResponsiveContainer width="100%" height={300}>
                <PieChart>
                  <Pie
                    data={categoryData}
                    cx="50%"
                    cy="50%"
                    labelLine={false}
                    label={({ name, percent }) => `${name}: ${(percent * 100).toFixed(0)}%`}
                    outerRadius={80}
                    fill="#8884d8"
                    dataKey="value"
                  >
                    {categoryData.map((entry, index) => (
                      <Cell key={`cell-${index}`} fill={entry.color} />
                    ))}
                  </Pie>
                  <Tooltip formatter={(value) => formatCurrency(value)} />
                </PieChart>
              </ResponsiveContainer>
            ) : (
              <p className="text-center text-gray-500 py-12">No category data</p>
            )}
          </CardContent>
        </Card>

        {/* Line Chart - Spending Over Time */}
        <Card>
          <CardHeader>
            <CardTitle>Spending Over Time</CardTitle>
          </CardHeader>
          <CardContent>
            {monthlyData.length > 0 ? (
              <ResponsiveContainer width="100%" height={300}>
                <LineChart data={monthlyData}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="month" />
                  <YAxis />
                  <Tooltip formatter={(value) => formatCurrency(value)} />
                  <Legend />
                  <Line
                    type="monotone"
                    dataKey="income"
                    stroke="#22c55e"
                    strokeWidth={2}
                    name="Income"
                  />
                  <Line
                    type="monotone"
                    dataKey="expense"
                    stroke="#ef4444"
                    strokeWidth={2}
                    name="Expense"
                  />
                </LineChart>
              </ResponsiveContainer>
            ) : (
              <p className="text-center text-gray-500 py-12">No monthly data</p>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}

export default Analytics;