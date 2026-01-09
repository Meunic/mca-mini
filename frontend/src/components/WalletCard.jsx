import React from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Alert, AlertDescription } from '@/components/ui/alert';
import { Wallet, Plus, AlertCircle } from 'lucide-react';
import { formatCurrency, isOverdrawn, getOverdraftAmount } from '@/utils/currency';

function WalletCard({ balance, onAddMoney, onViewHistory }) {
  const isNegative = isOverdrawn(balance);
  const overdraftAmount = getOverdraftAmount(balance);

  return (
    <Card className="card-hover border-2" data-testid="wallet-card">
      <CardHeader className="flex flex-row items-center justify-between pb-2">
        <CardTitle className="text-sm font-medium text-gray-600">Wallet Balance</CardTitle>
        <Wallet className="w-5 h-5 text-purple-600" />
      </CardHeader>
      <CardContent className="space-y-4">
        <div>
          <div
            className={`text-3xl font-bold ${
              isNegative ? 'text-red-600' : 'text-green-600'
            }`}
            data-testid="wallet-balance"
          >
            {formatCurrency(balance)}
          </div>
          <p className="text-xs text-gray-500 mt-1">Current month balance</p>
        </div>

        {isNegative && (
          <Alert variant="destructive" className="py-2">
            <AlertCircle className="h-4 w-4" />
            <AlertDescription className="text-xs">
              You are overdrawn by {formatCurrency(overdraftAmount)}
            </AlertDescription>
          </Alert>
        )}

        <div className="flex gap-2">
          <Button
            onClick={onAddMoney}
            className="flex-1 gradient-bg text-white"
            size="sm"
            data-testid="add-money-button"
          >
            <Plus className="w-4 h-4 mr-1" />
            Add Money
          </Button>
          <Button
            onClick={onViewHistory}
            variant="outline"
            size="sm"
            className="flex-1"
            data-testid="view-history-button"
          >
            History
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}

export default WalletCard;