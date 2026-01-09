import React from 'react';
import {createStackNavigator} from '@react-navigation/stack';
import {useAuth} from '../context/AuthContext';
import {ActivityIndicator, View} from 'react-native';

// Auth Screens
import AuthScreen from '../screens/AuthScreen';

// Main Screens
import DashboardScreen from '../screens/DashboardScreen';
import AddExpenseScreen from '../screens/AddExpenseScreen';
import CategoryScreen from '../screens/CategoryScreen';
import BudgetScreen from '../screens/BudgetScreen';
import AnalyticsScreen from '../screens/AnalyticsScreen';

const Stack = createStackNavigator();

const AppNavigator = () => {
  const {user, loading} = useAuth();

  if (loading) {
    return (
      <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
        <ActivityIndicator size="large" color="#8B5CF6" />
      </View>
    );
  }

  return (
    <Stack.Navigator
      screenOptions={{
        headerStyle: {
          backgroundColor: '#8B5CF6',
        },
        headerTintColor: '#fff',
        headerTitleStyle: {
          fontWeight: 'bold',
        },
      }}>
      {!user ? (
        <Stack.Screen
          name="Auth"
          component={AuthScreen}
          options={{headerShown: false}}
        />
      ) : (
        <>
          <Stack.Screen
            name="Dashboard"
            component={DashboardScreen}
            options={{title: 'SmartExpense'}}
          />
          <Stack.Screen
            name="AddExpense"
            component={AddExpenseScreen}
            options={{title: 'Add Transaction'}}
          />
          <Stack.Screen
            name="Categories"
            component={CategoryScreen}
            options={{title: 'Manage Categories'}}
          />
          <Stack.Screen
            name="Budgets"
            component={BudgetScreen}
            options={{title: 'Manage Budgets'}}
          />
          <Stack.Screen
            name="Analytics"
            component={AnalyticsScreen}
            options={{title: 'Analytics'}}
          />
        </>
      )}
    </Stack.Navigator>
  );
};

export default AppNavigator;
