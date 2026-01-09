import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Sparkles, Loader2, TrendingUp, Calendar } from 'lucide-react';
import axios from 'axios';
import { formatCurrency } from '@/utils/currency';
import { toast } from 'sonner';

const API_BASE = process.env.REACT_APP_BACKEND_URL + '/api';

function SpendingForecast() {
  const [loading, setLoading] = useState(false);
  const [forecast, setForecast] = useState(null);

  const getAuthHeaders = () => ({
    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
  });

  const handleGetForecast = async () => {
    setLoading(true);
    try {
      const response = await axios.post(
        `${API_BASE}/ai/forecast`,
        { months: 1 },
        getAuthHeaders()
      );

      setForecast(response.data);
    } catch (error) {
      console.error('AI forecast error:', error);
      toast.error('Failed to generate forecast');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card data-testid="spending-forecast-card">
      <CardHeader>
        <CardTitle className="flex items-center gap-2 text-lg">
          <TrendingUp className="w-5 h-5 text-purple-600" />
          Spending Forecast
        </CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        {!forecast ? (
          <Button
            onClick={handleGetForecast}
            disabled={loading}
            className="w-full"
            data-testid="generate-forecast-button"
          >
            {loading ? (
              <>
                <Loader2 className="w-4 h-4 mr-2 animate-spin" />
                Forecasting...
              </>
            ) : (
              <>
                <Sparkles className="w-4 h-4 mr-2" />
                Generate Forecast
              </>
            )}
          </Button>
        ) : (
          <>
            <div className="space-y-3">
              <div className="bg-gradient-to-r from-purple-50 to-pink-50 p-4 rounded-lg border border-purple-200">
                <div className="flex items-center justify-between mb-2">
                  <span className="text-sm font-medium text-gray-700">Safe to Spend Today</span>
                  <Calendar className="w-4 h-4 text-purple-600" />
                </div>
                <div className="text-2xl font-bold text-purple-600" data-testid="safe-to-spend">
                  {formatCurrency(forecast.safeToSpendToday)}
                </div>
                <p className="text-xs text-gray-600 mt-1">
                  Based on your average monthly spending
                </p>
              </div>

              {forecast.forecastByMonth && forecast.forecastByMonth.length > 0 && (
                <div>
                  <h4 className="font-semibold text-sm mb-2">Next Month Prediction</h4>
                  <div className="flex items-center justify-between p-3 bg-gray-50 rounded-lg">
                    <span className="text-sm">{forecast.forecastByMonth[0].month}</span>
                    <Badge variant="secondary">
                      {formatCurrency(forecast.forecastByMonth[0].total)}
                    </Badge>
                  </div>
                </div>
              )}

              {forecast.byCategory && forecast.byCategory.length > 0 && (
                <div>
                  <h4 className="font-semibold text-sm mb-2">By Category</h4>
                  <div className="space-y-2">
                    {forecast.byCategory.slice(0, 5).map((cat, index) => (
                      <div key={index} className="flex items-center justify-between text-sm">
                        <span className="text-gray-700">{cat.category}</span>
                        <span className="font-medium">{formatCurrency(cat.predicted)}</span>
                      </div>
                    ))}
                  </div>
                </div>
              )}

              <Button
                variant="outline"
                onClick={handleGetForecast}
                className="w-full"
                disabled={loading}
              >
                Refresh Forecast
              </Button>

              {forecast.fallback && (
                <p className="text-xs text-gray-500 text-center">
                  Using historical averages (AI forecasting unavailable)
                </p>
              )}
            </div>
          </>
        )}
      </CardContent>
    </Card>
  );
}

export default SpendingForecast;