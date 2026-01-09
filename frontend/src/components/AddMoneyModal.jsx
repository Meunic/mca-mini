import React, { useState } from 'react';
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Upload } from 'lucide-react';

const PAYMENT_METHODS = ['Cash', 'UPI', 'Debit Card', 'Credit Card'];

function AddMoneyModal({ open, onClose, onSubmit }) {
  const [formData, setFormData] = useState({
    amount: '',
    date: new Date().toISOString().split('T')[0],
    source: 'UPI',
    note: '',
    receipt: null
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({
      amount: parseFloat(formData.amount),
      date: formData.date,
      method: formData.source,
      note: formData.note,
      receipt: formData.receipt,
      category: 'Income',
      type: 'income'
    });
    
    // Reset form
    setFormData({
      amount: '',
      date: new Date().toISOString().split('T')[0],
      source: 'UPI',
      note: '',
      receipt: null
    });
  };

  const handleFileChange = (e) => {
    const file = e.target.files?.[0];
    if (file) {
      setFormData({ ...formData, receipt: file });
    }
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md" data-testid="add-money-modal">
        <DialogHeader>
          <DialogTitle>Add Money to Wallet</DialogTitle>
          <DialogDescription>
            Credit your wallet with incoming money
          </DialogDescription>
        </DialogHeader>
        <form onSubmit={handleSubmit}>
          <div className="space-y-4 py-4">
            <div className="space-y-2">
              <Label htmlFor="money-amount">Amount (₹)</Label>
              <Input
                id="money-amount"
                data-testid="money-amount-input"
                type="number"
                step="0.01"
                placeholder="0.00"
                value={formData.amount}
                onChange={(e) => setFormData({ ...formData, amount: e.target.value })}
                required
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="money-date">Date</Label>
              <Input
                id="money-date"
                data-testid="money-date-input"
                type="date"
                value={formData.date}
                onChange={(e) => setFormData({ ...formData, date: e.target.value })}
                required
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="money-source">Source / Payment Method</Label>
              <Select
                value={formData.source}
                onValueChange={(value) => setFormData({ ...formData, source: value })}
              >
                <SelectTrigger data-testid="money-source-select">
                  <SelectValue placeholder="Select source" />
                </SelectTrigger>
                <SelectContent>
                  {PAYMENT_METHODS.map(method => (
                    <SelectItem key={method} value={method}>{method}</SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="space-y-2">
              <Label htmlFor="money-note">Note (optional)</Label>
              <Textarea
                id="money-note"
                data-testid="money-note-input"
                placeholder="e.g., Salary, Gift, Refund..."
                value={formData.note}
                onChange={(e) => setFormData({ ...formData, note: e.target.value })}
                rows={3}
              />
            </div>

            <div className="space-y-2">
              <Label htmlFor="money-receipt">Receipt (optional)</Label>
              <div className="flex items-center gap-2">
                <Input
                  id="money-receipt"
                  data-testid="money-receipt-input"
                  type="file"
                  accept="image/*,.pdf"
                  onChange={handleFileChange}
                  className="flex-1"
                />
                <Upload className="w-5 h-5 text-gray-400" />
              </div>
            </div>
          </div>

          <DialogFooter>
            <Button type="button" variant="outline" onClick={onClose}>
              Cancel
            </Button>
            <Button type="submit" data-testid="money-submit-button" className="gradient-bg text-white">
              Add Money
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  );
}

export default AddMoneyModal;