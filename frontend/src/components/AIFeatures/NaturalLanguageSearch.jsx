import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Search, Sparkles, Loader2, X } from 'lucide-react';
import axios from 'axios';
import { formatCurrency } from '@/utils/currency';

const API_BASE = process.env.REACT_APP_BACKEND_URL + '/api';

function NaturalLanguageSearch({ onResults }) {
  const [query, setQuery] = useState('');
  const [loading, setLoading] = useState(false);
  const [results, setResults] = useState(null);

  const getAuthHeaders = () => ({
    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
  });

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!query.trim()) return;

    setLoading(true);
    try {
      const response = await axios.post(
        `${API_BASE}/ai/search`,
        { query },
        getAuthHeaders()
      );

      setResults(response.data);
      if (onResults) {
        onResults(response.data.results);
      }
    } catch (error) {
      console.error('AI search error:', error);
    } finally {
      setLoading(false);
    }
  };

  const clearSearch = () => {
    setQuery('');
    setResults(null);
    if (onResults) {
      onResults(null);
    }
  };

  return (
    <Card data-testid="ai-search-card">
      <CardHeader>
        <CardTitle className="flex items-center gap-2 text-lg">
          <Sparkles className="w-5 h-5 text-purple-600" />
          Natural Language Search
        </CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        <form onSubmit={handleSearch} className="flex gap-2\">
          <div className="relative flex-1\">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400\" />
            <Input
              placeholder="Ask: Show UPI payments above ₹500 last month\"
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              className="pl-10\"
              data-testid="ai-search-input\"
            />
          </div>
          <Button
            type="submit\"
            disabled={loading || !query.trim()}
            data-testid="ai-search-button\"
          >
            {loading ? (
              <Loader2 className="w-4 h-4 animate-spin\" />
            ) : (
              'Search'
            )}
          </Button>
          {results && (
            <Button
              type="button\"
              variant="ghost\"
              size="icon\"
              onClick={clearSearch}
              data-testid="ai-search-clear\"
            >
              <X className="w-4 h-4\" />
            </Button>
          )}
        </form>

        {results && (
          <div className="space-y-3\">
            <div className="flex items-center justify-between\">
              <p className="text-sm text-gray-600\">{results.query_summary}</p>
              {results.fallback && (
                <Badge variant="secondary\" className="text-xs\">
                  Using fallback rules
                </Badge>
              )}
            </div>

            {results.results && results.results.length > 0 && (
              <div className="text-sm text-gray-500\">
                Showing {results.results.length} transaction(s)
              </div>
            )}
          </div>
        )}

        <div className="text-xs text-gray-500 space-y-1\">
          <p className="font-medium\">Try asking:</p>
          <ul className="list-disc pl-4 space-y-0.5\">
            <li>\"Show food expenses this month\"</li>
            <li>\"UPI payments above ₹1000\"</li>
            <li>\"Transportation expenses last week\"</li>
            <li>\"Income transactions this year\"</li>
          </ul>
        </div>
      </CardContent>
    </Card>
  );
}

export default NaturalLanguageSearch;
