import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Sparkles, Loader2, TrendingUp } from 'lucide-react';
import axios from 'axios';
import { formatCurrency } from '@/utils/currency';
import { toast } from 'sonner';

const API_BASE = process.env.REACT_APP_BACKEND_URL + '/api';

function BudgetSuggestions({ onApply }) {
  const [loading, setLoading] = useState(false);
  const [suggestions, setSuggestions] = useState(null);
  const [showDialog, setShowDialog] = useState(false);

  const getAuthHeaders = () => ({
    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
  });

  const handleGetSuggestions = async () => {
    setLoading(true);
    try {
      const response = await axios.post(
        `${API_BASE}/ai/suggest-budget`,
        { months: 3 },
        getAuthHeaders()
      );

      setSuggestions(response.data);
      setShowDialog(true);
    } catch (error) {
      console.error('AI budget suggestion error:', error);
      toast.error('Failed to get budget suggestions');
    } finally {
      setLoading(false);
    }
  };

  const handleApplyBudget = async (suggestion) => {
    if (onApply) {
      await onApply(suggestion);
    }
    toast.success(`Budget for ${suggestion.category} applied`);
  };

  return (
    <>
      <Button
        onClick={handleGetSuggestions}
        disabled={loading}
        className="gradient-bg text-white"
        data-testid="ai-budget-suggest-button"
      >
        {loading ? (
          <>
            <Loader2 className="w-4 h-4 mr-2 animate-spin" />
            Getting AI Suggestions...
          </>
        ) : (
          <>
            <Sparkles className="w-4 h-4 mr-2" />
            AI Budget Suggestions
          </>
        )}
      </Button>

      <Dialog open={showDialog} onOpenChange={setShowDialog}>
        <DialogContent className="max-w-2xl" data-testid="budget-suggestions-dialog">
          <DialogHeader>
            <DialogTitle className="flex items-center gap-2">
              <Sparkles className="w-5 h-5 text-purple-600" />
              AI Budget Recommendations
            </DialogTitle>
            <DialogDescription>
              Based on your last 3 months of spending patterns
            </DialogDescription>
          </DialogHeader>

          <div className="space-y-3 max-h-96 overflow-y-auto">
            {suggestions?.suggestions?.map((suggestion, index) => (
              <Card key={index} className="border-l-4 border-l-purple-500">
                <CardContent className="pt-4">
                  <div className="flex items-start justify-between">
                    <div className="flex-1">
                      <div className="flex items-center gap-2 mb-2">
                        <h4 className="font-semibold">{suggestion.category}</h4>
                        <Badge variant="secondary">
                          {formatCurrency(suggestion.suggestedMonthlyBudget)}/month
                        </Badge>
                      </div>
                      <p className="text-sm text-gray-600">{suggestion.rationale}</p>
                    </div>
                    <Button
                      size="sm"
                      onClick={() => handleApplyBudget(suggestion)}
                      data-testid={`apply-budget-${suggestion.category}`}
                    >
                      Apply
                    </Button>
                  </div>
                </CardContent>
              </Card>
            ))}

            {suggestions?.fallback && (
              <p className="text-xs text-gray-500 text-center">
                Using rule-based suggestions (AI temporarily unavailable)
              </p>
            )}
          </div>

          <DialogFooter>
            <Button variant="outline" onClick={() => setShowDialog(false)}>
              Close
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </>
  );
}

export default BudgetSuggestions;