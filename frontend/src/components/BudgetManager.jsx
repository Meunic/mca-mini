import React, { useState } from 'react';
import axios from 'axios';
import { toast } from 'sonner';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Progress } from '@/components/ui/progress';
import { Plus, Trash2, AlertCircle } from 'lucide-react';
import { formatCurrency } from '@/utils/currency';

const API_BASE = process.env.REACT_APP_BACKEND_URL + '/api';

function BudgetManager({ budgets, categories, expenses, onUpdate }) {
  const [showAdd, setShowAdd] = useState(false);
  const [newBudget, setNewBudget] = useState({
    category: '',
    amount: '',
    period: 'monthly',
    start_date: new Date().toISOString().split('T')[0],
    end_date: new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().split('T')[0]
  });

  const getAuthHeaders = () => ({
    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
  });

  const calculateBudgetProgress = (budget) => {
    const categoryExpenses = expenses.filter(exp => 
      exp.category === budget.category &&
      exp.type !== 'income' &&
      exp.date >= budget.start_date &&
      exp.date <= budget.end_date
    );
    const spent = categoryExpenses.reduce((sum, exp) => sum + exp.amount, 0);
    const percentage = (spent / budget.amount) * 100;
    return { spent, percentage: Math.min(percentage, 100) };
  };

  const handleAddBudget = async (e) => {
    e.preventDefault();
    try {
      await axios.post(`${API_BASE}/budgets`, {
        ...newBudget,
        amount: parseFloat(newBudget.amount)
      }, getAuthHeaders());
      toast.success('Budget created');
      setShowAdd(false);
      setNewBudget({
        category: '',
        amount: '',
        period: 'monthly',
        start_date: new Date().toISOString().split('T')[0],
        end_date: new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().split('T')[0]
      });
      onUpdate();
    } catch (error) {
      toast.error('Failed to create budget');
    }
  };

  const handleDeleteBudget = async (budgetId) => {
    if (!window.confirm('Are you sure you want to delete this budget?')) return;

    try {
      await axios.delete(`${API_BASE}/budgets/${budgetId}`, getAuthHeaders());
      toast.success('Budget deleted');
      onUpdate();
    } catch (error) {
      toast.error('Failed to delete budget');
    }
  };

  return (
    <div data-testid="budget-manager">
      <Card>
        <CardHeader>
          <div className="flex items-center justify-between">
            <CardTitle className="text-base">Your Budgets</CardTitle>
            <Button
              size="sm"
              onClick={() => setShowAdd(true)}
              data-testid="add-budget-button"
            >
              <Plus className="w-4 h-4 mr-1" />
              Add
            </Button>
          </div>
        </CardHeader>
        <CardContent>
          {budgets.length === 0 ? (
            <p className="text-sm text-gray-500 text-center py-4">No budgets yet</p>
          ) : (
            <div className="space-y-4">
              {budgets.map(budget => {
                const { spent, percentage } = calculateBudgetProgress(budget);
                const isOverBudget = percentage >= 100;

                return (
                  <div
                    key={budget.id}
                    className="p-3 border rounded-lg"
                    data-testid={`budget-item-${budget.id}`}
                  >
                    <div className="flex items-start justify-between mb-2">
                      <div>
                        <div className="font-medium">{budget.category}</div>
                        <div className="text-sm text-gray-500">
                          {formatCurrency(spent)} / {formatCurrency(budget.amount)}
                        </div>
                      </div>
                      <Button
                        variant="ghost"
                        size="icon"
                        onClick={() => handleDeleteBudget(budget.id)}
                        data-testid={`delete-budget-${budget.id}`}
                      >
                        <Trash2 className="w-4 h-4 text-red-500" />
                      </Button>
                    </div>
                    <Progress
                      value={percentage}
                      className="h-2"
                      data-testid={`budget-progress-${budget.id}`}
                    />
                    {isOverBudget && (
                      <div className="flex items-center gap-1 mt-2 text-xs text-red-600">
                        <AlertCircle className="w-3 h-3" />
                        <span>Over budget!</span>
                      </div>
                    )}
                  </div>
                );
              })}
            </div>
          )}
        </CardContent>
      </Card>

      <Dialog open={showAdd} onOpenChange={setShowAdd}>
        <DialogContent data-testid="add-budget-modal">
          <DialogHeader>
            <DialogTitle>Create Budget</DialogTitle>
            <DialogDescription>Set a spending limit for a category</DialogDescription>
          </DialogHeader>
          <form onSubmit={handleAddBudget}>
            <div className="space-y-4 py-4">
              <div className="space-y-2">
                <Label htmlFor="budget-category">Category</Label>
                <Select
                  value={newBudget.category}
                  onValueChange={(value) => setNewBudget({ ...newBudget, category: value })}
                >
                  <SelectTrigger data-testid="budget-category-select">
                    <SelectValue placeholder="Select category" />
                  </SelectTrigger>
                  <SelectContent>
                    {categories.map(cat => (
                      <SelectItem key={cat.id} value={cat.name}>
                        {cat.icon} {cat.name}
                      </SelectItem>
                    ))}
                  </SelectContent>
                </Select>
              </div>
              <div className="space-y-2">
                <Label htmlFor="budget-amount">Budget Amount (₹)</Label>
                <Input
                  id="budget-amount"
                  data-testid="budget-amount-input"
                  type="number"
                  step="0.01"
                  placeholder="5000.00"
                  value={newBudget.amount}
                  onChange={(e) => setNewBudget({ ...newBudget, amount: e.target.value })}
                  required
                />
              </div>
              <div className="space-y-2">
                <Label htmlFor="budget-period">Period</Label>
                <Select
                  value={newBudget.period}
                  onValueChange={(value) => setNewBudget({ ...newBudget, period: value })}
                >
                  <SelectTrigger data-testid="budget-period-select">
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="weekly">Weekly</SelectItem>
                    <SelectItem value="monthly">Monthly</SelectItem>
                    <SelectItem value="yearly">Yearly</SelectItem>
                  </SelectContent>
                </Select>
              </div>
              <div className="grid grid-cols-2 gap-4">
                <div className="space-y-2">
                  <Label htmlFor="budget-start">Start Date</Label>
                  <Input
                    id="budget-start"
                    data-testid="budget-start-input"
                    type="date"
                    value={newBudget.start_date}
                    onChange={(e) => setNewBudget({ ...newBudget, start_date: e.target.value })}
                    required
                  />
                </div>
                <div className="space-y-2">
                  <Label htmlFor="budget-end">End Date</Label>
                  <Input
                    id="budget-end"
                    data-testid="budget-end-input"
                    type="date"
                    value={newBudget.end_date}
                    onChange={(e) => setNewBudget({ ...newBudget, end_date: e.target.value })}
                    required
                  />
                </div>
              </div>
            </div>
            <DialogFooter>
              <Button type="button" variant="outline" onClick={() => setShowAdd(false)}>
                Cancel
              </Button>
              <Button type="submit" data-testid="budget-submit-button">Create Budget</Button>
            </DialogFooter>
          </form>
        </DialogContent>
      </Dialog>
    </div>
  );
}

export default BudgetManager;