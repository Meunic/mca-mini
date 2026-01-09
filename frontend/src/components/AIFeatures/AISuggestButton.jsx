import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Sparkles, Loader2 } from 'lucide-react';
import axios from 'axios';

const API_BASE = process.env.REACT_APP_BACKEND_URL + '/api';

function AISuggestButton({ text, amount, onSuggestion }) {
  const [loading, setLoading] = useState(false);
  const [confidence, setConfidence] = useState(null);

  const getAuthHeaders = () => ({
    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
  });

  const handleSuggest = async () => {
    if (!text) return;

    setLoading(true);
    try {
      const response = await axios.post(
        `${API_BASE}/ai/suggest-expense`,
        { text, amount },
        getAuthHeaders()
      );

      setConfidence(response.data.confidence);
      onSuggestion(response.data);
    } catch (error) {
      console.error('AI suggestion error:', error);
      // Fallback handled by backend
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex items-center gap-2">
      <Button
        type="button"
        variant="outline"
        size="sm"
        onClick={handleSuggest}
        disabled={loading || !text}
        data-testid="ai-suggest-button"
        className="border-purple-200 hover:bg-purple-50"
      >
        {loading ? (
          <>
            <Loader2 className="w-4 h-4 mr-2 animate-spin" />
            AI Suggesting...
          </>
        ) : (
          <>
            <Sparkles className="w-4 h-4 mr-2 text-purple-600" />
            AI Suggest
          </>
        )}
      </Button>
      
      {confidence !== null && (
        <Badge variant="secondary" className="text-xs">
          {Math.round(confidence * 100)}% confident
        </Badge>
      )}
    </div>
  );
}

export default AISuggestButton;
