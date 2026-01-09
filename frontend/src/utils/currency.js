// Currency configuration and formatting utilities for Indian Rupees

export const CURRENCY = {
  symbol: '₹',
  locale: 'en-IN',
  currency: 'INR'
};

/**
 * Format amount to Indian Rupee format
 * @param {number} amount - The amount to format
 * @param {boolean} showSymbol - Whether to show the ₹ symbol (default: true)
 * @returns {string} Formatted currency string
 */
export const formatCurrency = (amount, showSymbol = true) => {
  if (amount === null || amount === undefined || isNaN(amount)) {
    return showSymbol ? `${CURRENCY.symbol} 0.00` : '0.00';
  }

  const formatted = Math.abs(amount).toLocaleString(CURRENCY.locale, {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  });

  const sign = amount < 0 ? '-' : '';
  
  if (showSymbol) {
    return `${sign}${CURRENCY.symbol} ${formatted}`;
  }
  
  return `${sign}${formatted}`;
};

/**
 * Format amount for display with color coding
 * @param {number} amount - The amount to format
 * @param {string} type - Transaction type: 'income' or 'expense'
 * @returns {object} Object with formatted string and color class
 */
export const formatCurrencyWithType = (amount, type) => {
  const formatted = formatCurrency(amount);
  const color = type === 'income' ? 'text-green-600' : 'text-red-600';
  const prefix = type === 'income' ? '+' : '-';
  
  return {
    formatted: `${prefix}${formatted}`,
    color
  };
};

/**
 * Parse Indian currency input string to number
 * @param {string} str - Currency string to parse
 * @returns {number} Parsed number
 */
export const parseCurrency = (str) => {
  if (!str) return 0;
  
  // Remove currency symbol, commas, and spaces
  const cleaned = str.replace(/[₹,\s]/g, '');
  return parseFloat(cleaned) || 0;
};

/**
 * Check if wallet balance is negative
 * @param {number} balance - Wallet balance
 * @returns {boolean} True if balance is negative
 */
export const isOverdrawn = (balance) => {
  return balance < 0;
};

/**
 * Get overdraft amount (absolute value if negative)
 * @param {number} balance - Wallet balance
 * @returns {number} Overdraft amount or 0
 */
export const getOverdraftAmount = (balance) => {
  return balance < 0 ? Math.abs(balance) : 0;
};

export default {
  CURRENCY,
  formatCurrency,
  formatCurrencyWithType,
  parseCurrency,
  isOverdrawn,
  getOverdraftAmount
};