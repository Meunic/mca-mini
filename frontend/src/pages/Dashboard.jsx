import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'sonner';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import { Badge } from '@/components/ui/badge';
import ExpenseList from '@/components/ExpenseList';
import AddExpenseModal from '@/components/AddExpenseModal';
import AddMoneyModal from '@/components/AddMoneyModal';
import WalletCard from '@/components/WalletCard';
import CategoryManager from '@/components/CategoryManager';
import BudgetManager from '@/components/BudgetManager';
import Analytics from '@/components/Analytics';
import NaturalLanguageSearch from '@/components/AIFeatures/NaturalLanguageSearch';
import BudgetSuggestions from '@/components/AIFeatures/BudgetSuggestions';
import AIInsights from '@/components/AIFeatures/AIInsights';
import SpendingForecast from '@/components/AIFeatures/SpendingForecast';
import { Wallet, LogOut, Plus, TrendingUp, TrendingDown, Target, Calendar } from 'lucide-react';
import { formatCurrency } from '@/utils/currency';

const API_BASE = process.env.REACT_APP_BACKEND_URL + '/api';

function Dashboard() {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [expenses, setExpenses] = useState([]);
  const [categories, setCategories] = useState([]);
  const [budgets, setBudgets] = useState([]);
  const [overview, setOverview] = useState(null);
  const [loading, setLoading] = useState(true);
  const [showAddExpense, setShowAddExpense] = useState(false);
  const [showAddMoney, setShowAddMoney] = useState(false);
  const [editingExpense, setEditingExpense] = useState(null);
  const [walletBalance, setWalletBalance] = useState(0);
  const [filteredExpenses, setFilteredExpenses] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const userData = localStorage.getItem('user');

    if (!token) {
      navigate('/auth');
      return;
    }

    setUser(JSON.parse(userData));
    loadData();
  }, [navigate]);

  const getAuthHeaders = () => ({
    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
  });

  const loadData = async () => {
    try {
      const [transactionsRes, categoriesRes, budgetsRes, overviewRes, walletRes] = await Promise.all([
        axios.get(`${API_BASE}/transactions`, getAuthHeaders()),
        axios.get(`${API_BASE}/categories`, getAuthHeaders()),
        axios.get(`${API_BASE}/budgets`, getAuthHeaders()),
        axios.get(`${API_BASE}/analytics/overview`, getAuthHeaders()),
        axios.get(`${API_BASE}/wallet`, getAuthHeaders())
      ]);

      setExpenses(transactionsRes.data);
      setCategories(categoriesRes.data);
      setBudgets(budgetsRes.data);
      setOverview(overviewRes.data);
      setWalletBalance(walletRes.data.balance);
    } catch (error) {
      if (error.response?.status === 401) {
        localStorage.clear();
        navigate('/auth');
      } else {
        toast.error('Failed to load data');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate('/auth');
    toast.success('Logged out successfully');
  };

  const handleAddExpense = () => {
    setEditingExpense(null);
    setShowAddExpense(true);
  };

  const handleEditExpense = (expense) => {
    setEditingExpense(expense);
    setShowAddExpense(true);
  };

  const handleDeleteExpense = async (expenseId) => {
    try {
      await axios.delete(`${API_BASE}/transactions/${expenseId}`, getAuthHeaders());
      toast.success('Transaction deleted');
      loadData();
    } catch (error) {
      toast.error('Failed to delete transaction');
    }
  };

  const handleExpenseSubmit = async (expenseData) => {
    try {
      const formData = new FormData();
      Object.keys(expenseData).forEach(key => {
        if (expenseData[key] !== null && expenseData[key] !== undefined) {
          formData.append(key, expenseData[key]);
        }
      });

      if (editingExpense) {
        await axios.put(`${API_BASE}/transactions/${editingExpense.id}`, expenseData, getAuthHeaders());
        toast.success('Transaction updated');
      } else {
        await axios.post(`${API_BASE}/transactions`, formData, getAuthHeaders());
        toast.success(`Expense added - Debited from wallet`);
      }

      setShowAddExpense(false);
      setEditingExpense(null);
      loadData();
    } catch (error) {
      toast.error('Failed to save transaction');
    }
  };

  const handleAddMoney = () => {
    setShowAddMoney(true);
  };

  const handleMoneySubmit = async (moneyData) => {
    try {
      const formData = new FormData();
      Object.keys(moneyData).forEach(key => {
        if (moneyData[key] !== null && moneyData[key] !== undefined) {
          formData.append(key, moneyData[key]);
        }
      });

      await axios.post(`${API_BASE}/transactions`, formData, getAuthHeaders());
      toast.success(`${formatCurrency(moneyData.amount)} added to wallet`);
      setShowAddMoney(false);
      loadData();
    } catch (error) {
      toast.error('Failed to add money');
    }
  };

  const handleViewHistory = () => {
    // Scroll to transaction list
    document.getElementById('transaction-list')?.scrollIntoView({ behavior: 'smooth' });
  };

  const handleSearchResults = (results) => {
    setFilteredExpenses(results);
  };

  const handleApplyBudget = async (suggestion) => {
    try {
      await axios.post(`${API_BASE}/budgets`, {
        category: suggestion.category,
        amount: suggestion.suggestedMonthlyBudget,
        period: 'monthly',
        start_date: new Date().toISOString().split('T')[0],
        end_date: new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().split('T')[0]
      }, getAuthHeaders());
      
      toast.success(`Budget for ${suggestion.category} created`);
      loadData();
    } catch (error) {
      toast.error('Failed to create budget');
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-lg">Loading...</div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50" data-testid="dashboard-page">
      {/* Header */}
      <header className="gradient-bg text-white shadow-lg">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-3">
              <Wallet className="w-8 h-8" />
              <h1 className="text-2xl font-bold">SmartExpense</h1>
            </div>
            <div className="flex items-center gap-4">
              <div className="hidden sm:block text-right">
                <div className="font-semibold" data-testid="user-name">{user?.name}</div>
                <div className="text-sm opacity-90" data-testid="user-email">{user?.email}</div>
              </div>
              <Avatar>
                <AvatarFallback className="bg-white text-purple-600 font-semibold">
                  {user?.name?.charAt(0).toUpperCase()}
                </AvatarFallback>
              </Avatar>
              <Button
                variant="outline"
                size="icon"
                className="bg-white/10 border-white/20 hover:bg-white/20 text-white"
                onClick={handleLogout}
                data-testid="logout-button"
              >
                <LogOut className="w-4 h-4" />
              </Button>
            </div>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8">
        {/* Overview Section */}
        <section className="mb-8">
          <h2 className="text-2xl font-bold mb-4">Overview</h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
            <WalletCard 
              balance={walletBalance}
              onAddMoney={handleAddMoney}
              onViewHistory={handleViewHistory}
            />

            <Card className="card-hover">
              <CardHeader className="flex flex-row items-center justify-between pb-2">
                <CardTitle className="text-sm font-medium text-gray-600">Monthly Income</CardTitle>
                <TrendingUp className="w-4 h-4 text-green-500" />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold text-green-600" data-testid="monthly-income">
                  {formatCurrency(overview?.monthly_income || 0)}
                </div>
              </CardContent>
            </Card>

            <Card className="card-hover">
              <CardHeader className="flex flex-row items-center justify-between pb-2">
                <CardTitle className="text-sm font-medium text-gray-600">Monthly Spending</CardTitle>
                <TrendingDown className="w-4 h-4 text-red-500" />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold text-red-600" data-testid="monthly-spending">
                  {formatCurrency(overview?.monthly_spending || 0)}
                </div>
              </CardContent>
            </Card>

            <Card className="card-hover">
              <CardHeader className="flex flex-row items-center justify-between pb-2">
                <CardTitle className="text-sm font-medium text-gray-600">Active Budgets</CardTitle>
                <Target className="w-4 h-4 text-blue-500" />
              </CardHeader>
              <CardContent>
                <div className="text-2xl font-bold" data-testid="active-budgets">
                  {overview?.active_budgets || 0}
                </div>
              </CardContent>
            </Card>
          </div>
        </section>

        {/* Quick Actions */}
        <section className="mb-8 flex gap-4">
          <Button
            onClick={handleAddExpense}
            className="gradient-bg text-white"
            data-testid="add-expense-button"
          >
            <Plus className="w-4 h-4 mr-2" />
            Add Expense
          </Button>
          <BudgetSuggestions onApply={handleApplyBudget} />
        </section>

        {/* Main Content Grid */}
        <div className="grid lg:grid-cols-3 gap-8">
          <div className="lg:col-span-2 space-y-8">
            {/* AI Search */}
            <section className="mb-8">
              <NaturalLanguageSearch onResults={handleSearchResults} />
            </section>

            {/* Recent Transactions */}
            <section id="transaction-list">
              <h2 className="text-2xl font-bold mb-4">Recent Transactions</h2>
              <ExpenseList
                expenses={filteredExpenses || expenses}
                categories={categories}
                onEdit={handleEditExpense}
                onDelete={handleDeleteExpense}
              />
            </section>

            {/* Analytics */}
            <section>
              <h2 className="text-2xl font-bold mb-4">Analytics</h2>
              <Analytics expenses={expenses} categories={categories} />
            </section>
          </div>

          <div className="space-y-8">
            {/* AI Features */}
            <section>
              <h2 className="text-2xl font-bold mb-4">AI Features</h2>
              <div className="space-y-4">
                <SpendingForecast />
                <AIInsights />
              </div>
            </section>

            {/* Categories */}
            <section>
              <h2 className="text-2xl font-bold mb-4">Categories</h2>
              <CategoryManager
                categories={categories}
                onUpdate={loadData}
              />
            </section>

            {/* Budgets */}
            <section>
              <h2 className="text-2xl font-bold mb-4">Budgets</h2>
              <BudgetManager
                budgets={budgets}
                categories={categories}
                expenses={expenses}
                onUpdate={loadData}
              />
            </section>
          </div>
        </div>
      </main>

      {/* Add/Edit Expense Modal */}
      {showAddExpense && (
        <AddExpenseModal
          open={showAddExpense}
          onClose={() => {
            setShowAddExpense(false);
            setEditingExpense(null);
          }}
          onSubmit={handleExpenseSubmit}
          categories={categories}
          editingExpense={editingExpense}
          walletBalance={walletBalance}
        />
      )}

      {/* Add Money Modal */}
      {showAddMoney && (
        <AddMoneyModal
          open={showAddMoney}
          onClose={() => setShowAddMoney(false)}
          onSubmit={handleMoneySubmit}
        />
      )}
    </div>
  );
}

export default Dashboard;