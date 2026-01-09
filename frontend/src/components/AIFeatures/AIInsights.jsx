import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Dialog, DialogContent, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Sparkles, Loader2, Lightbulb } from 'lucide-react';
import axios from 'axios';
import { toast } from 'sonner';

const API_BASE = process.env.REACT_APP_BACKEND_URL + '/api';

function AIInsights() {
  const [loading, setLoading] = useState(false);
  const [insights, setInsights] = useState(null);
  const [showDialog, setShowDialog] = useState(false);
  const [dateRange, setDateRange] = useState({
    from: new Date(new Date().setMonth(new Date().getMonth() - 1)).toISOString().split('T')[0],
    to: new Date().toISOString().split('T')[0]
  });

  const getAuthHeaders = () => ({
    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
  });

  const handleGetInsights = async () => {
    setLoading(true);
    try {
      const response = await axios.post(
        `${API_BASE}/ai/insights`,
        { range: dateRange },
        getAuthHeaders()
      );

      setInsights(response.data);
      setShowDialog(true);
    } catch (error) {
      console.error('AI insights error:', error);
      toast.error('Failed to generate insights');
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Card data-testid="ai-insights-card">
        <CardHeader>
          <CardTitle className="flex items-center gap-2 text-lg">
            <Sparkles className="w-5 h-5 text-purple-600" />
            AI Insights
          </CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <div>
              <Label htmlFor="insights-from">From</Label>
              <Input
                id="insights-from"
                type="date"
                value={dateRange.from}
                onChange={(e) => setDateRange({ ...dateRange, from: e.target.value })}
                data-testid="insights-from-date"
              />
            </div>
            <div>
              <Label htmlFor="insights-to">To</Label>
              <Input
                id="insights-to"
                type="date"
                value={dateRange.to}
                onChange={(e) => setDateRange({ ...dateRange, to: e.target.value })}
                data-testid="insights-to-date"
              />
            </div>
          </div>

          <Button
            onClick={handleGetInsights}
            disabled={loading}
            className="w-full"
            data-testid="generate-insights-button"
          >
            {loading ? (
              <>
                <Loader2 className="w-4 h-4 mr-2 animate-spin" />
                Generating Insights...
              </>
            ) : (
              <>
                <Lightbulb className="w-4 h-4 mr-2" />
                Generate Insights
              </>
            )}
          </Button>
        </CardContent>
      </Card>

      <Dialog open={showDialog} onOpenChange={setShowDialog}>
        <DialogContent className="max-w-2xl" data-testid="insights-dialog">
          <DialogHeader>
            <DialogTitle className="flex items-center gap-2">
              <Sparkles className="w-5 h-5 text-purple-600" />
              Your Financial Insights
            </DialogTitle>
          </DialogHeader>

          {insights && (
            <div className="space-y-4">
              <Card className="bg-purple-50 border-purple-200">
                <CardContent className="pt-4">
                  <p className="text-sm">{insights.summary}</p>
                </CardContent>
              </Card>

              <div>
                <h4 className="font-semibold mb-3">Key Highlights</h4>
                <div className="space-y-2">
                  {insights.highlights?.map((highlight, index) => (
                    <Card key={index}>
                      <CardContent className="pt-4">
                        <div className="font-medium text-sm">{highlight.title}</div>
                        <div className="text-sm text-gray-600">{highlight.detail}</div>
                      </CardContent>
                    </Card>
                  ))}
                </div>
              </div>

              {insights.recommendations && insights.recommendations.length > 0 && (
                <div>
                  <h4 className="font-semibold mb-3">Recommendations</h4>
                  <ul className="list-disc pl-5 space-y-1 text-sm text-gray-700">
                    {insights.recommendations.map((rec, index) => (
                      <li key={index}>{rec}</li>
                    ))}
                  </ul>
                </div>
              )}

              {insights.fallback && (
                <p className="text-xs text-gray-500 text-center">
                  Using rule-based analysis (AI temporarily unavailable)
                </p>
              )}
            </div>
          )}
        </DialogContent>
      </Dialog>
    </>
  );
}

export default AIInsights;