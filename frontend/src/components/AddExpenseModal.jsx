import React, { useState, useEffect } from 'react';
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Alert, AlertDescription } from '@/components/ui/alert';
import { Upload, AlertCircle } from 'lucide-react';
import { formatCurrency } from '@/utils/currency';
import AISuggestButton from '@/components/AIFeatures/AISuggestButton';

const PAYMENT_METHODS = ['Cash', 'UPI', 'Debit Card', 'Credit Card'];

function AddExpenseModal({ open, onClose, onSubmit, categories, editingExpense, walletBalance }) {
  const [formData, setFormData] = useState({
    amount: '',
    date: new Date().toISOString().split('T')[0],
    category: '',
    method: 'UPI',
    note: '',
    receipt: null
  });
  
  const [showOverdraftWarning, setShowOverdraftWarning] = useState(false);
  const [aiSuggested, setAiSuggested] = useState(false);

  useEffect(() => {
    if (editingExpense) {
      setFormData({
        amount: editingExpense.amount.toString(),
        date: editingExpense.date,
        category: editingExpense.category,
        method: editingExpense.method,
        note: editingExpense.note || '',
        receipt: null
      });
    } else {
      const defaultCategory = categories.find(c => c.name !== 'Income')?.name || categories[0]?.name || '';
      setFormData({
        amount: '',
        date: new Date().toISOString().split('T')[0],
        category: defaultCategory,
        method: 'UPI',
        note: '',
        receipt: null
      });
    }
  }, [editingExpense, categories, open]);

  useEffect(() => {
    // Check if expense would cause overdraft
    const amount = parseFloat(formData.amount) || 0;
    const wouldBeOverdrawn = !editingExpense && (walletBalance - amount) < 0;
    setShowOverdraftWarning(wouldBeOverdrawn);
  }, [formData.amount, walletBalance, editingExpense]);

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({
      ...formData,
      amount: parseFloat(formData.amount),
      type: 'expense'
    });
  };

  const handleFileChange = (e) => {
    const file = e.target.files?.[0];
    if (file) {
      setFormData({ ...formData, receipt: file });
    }
  };

  const handleAISuggestion = (suggestion) => {
    setFormData({
      ...formData,
      category: suggestion.category,
      method: suggestion.method || formData.method,
      note: suggestion.suggested_note || formData.note
    });
    setAiSuggested(true);
    setTimeout(() => setAiSuggested(false), 3000);
  };

  const expenseCategories = categories.filter(cat => cat.name !== 'Income');

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md" data-testid="add-expense-modal">
        <DialogHeader>
          <DialogTitle>{editingExpense ? 'Edit Expense' : 'Add Expense'}</DialogTitle>
          <DialogDescription>
            {editingExpense ? 'Update expense details' : 'Record your expense and debit from wallet'}
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit}>
          <div className="space-y-4 py-4">
            <div className="space-y-2">
              <Label htmlFor="amount">Amount (₹)</Label>
              <Input
                id="amount"
                data-testid="expense-amount-input"
                type="number"
                step="0.01"
                placeholder="0.00"
                value={formData.amount}
                onChange={(e) => setFormData({ ...formData, amount: e.target.value })}
                required
              />
              {!editingExpense && walletBalance !== undefined && (
                <p className="text-xs text-gray-500">
                  Wallet balance: {formatCurrency(walletBalance)}
                </p>
              )}
            </div>

            {showOverdraftWarning && (
              <Alert variant="destructive" className="py-2">
                <AlertCircle className="h-4 w-4" />
                <AlertDescription className="text-xs">
                  This expense will make your wallet negative. Balance after: {formatCurrency(walletBalance - parseFloat(formData.amount || 0))}
                </AlertDescription>
              </Alert>
            )}

            <div className="space-y-2">
              <Label htmlFor="date">Date</Label>
              <Input
                id="date"
                data-testid="expense-date-input"
                type="date"
                value={formData.date}
                onChange={(e) => setFormData({ ...formData, date: e.target.value })}
                required
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="category">Category</Label>
              <Select
                value={formData.category}
                onValueChange={(value) => setFormData({ ...formData, category: value })}
              >
                <SelectTrigger data-testid="expense-category-select">
                  <SelectValue placeholder="Select category" />
                </SelectTrigger>
                <SelectContent>
                  {expenseCategories.map(cat => (
                    <SelectItem key={cat.id} value={cat.name}>
                      {cat.icon} {cat.name}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="space-y-2">
              <Label htmlFor="method">Payment Method</Label>
              <Select
                value={formData.method}
                onValueChange={(value) => setFormData({ ...formData, method: value })}
              >
                <SelectTrigger data-testid="expense-method-select">
                  <SelectValue placeholder="Select method" />
                </SelectTrigger>
                <SelectContent>
                  {PAYMENT_METHODS.map(method => (
                    <SelectItem key={method} value={method}>{method}</SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="space-y-2">
              <Label htmlFor="note">Note (optional)</Label>
              <Textarea
                id="note"
                data-testid="expense-note-input"
                placeholder="Add a note about this expense..."
                value={formData.note}
                onChange={(e) => setFormData({ ...formData, note: e.target.value })}
                rows={3}
              />
            </div>

            {!editingExpense && (
              <div className="space-y-2">
                <Label>AI Assistance</Label>
                <AISuggestButton
                  text={formData.note}
                  amount={parseFloat(formData.amount) || undefined}
                  onSuggestion={handleAISuggestion}
                />
                {aiSuggested && (
                  <p className="text-xs text-green-600">✓ AI suggestions applied! You can still edit them.</p>
                )}
              </div>
            )}

            {!editingExpense && (
              <div className="space-y-2">
                <Label htmlFor="receipt">Receipt (optional)</Label>
                <div className="flex items-center gap-2">
                  <Input
                    id="receipt"
                    data-testid="expense-receipt-input"
                    type="file"
                    accept="image/*,.pdf"
                    onChange={handleFileChange}
                    className="flex-1"
                  />
                  <Upload className="w-5 h-5 text-gray-400" />
                </div>
              </div>
            )}
          </div>

          <DialogFooter>
            <Button type="button" variant="outline" onClick={onClose}>
              Cancel
            </Button>
            <Button type="submit" data-testid="expense-submit-button">
              {editingExpense ? 'Update' : 'Add Expense'}
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}

export default AddExpenseModal;