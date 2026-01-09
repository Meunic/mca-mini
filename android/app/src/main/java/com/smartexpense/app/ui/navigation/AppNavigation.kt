package com.smartexpense.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smartexpense.app.ui.screens.auth.AuthScreen
import com.smartexpense.app.ui.screens.auth.AuthViewModel
import com.smartexpense.app.ui.screens.dashboard.DashboardScreen
import com.smartexpense.app.ui.screens.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Auth : Screen("auth")
    object Dashboard : Screen("dashboard")
    object AddExpense : Screen("add_expense") // <--- ADDED THIS
    object Categories : Screen("categories")
    object Budgets : Screen("budgets")
    object Analytics : Screen("analytics")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // val authViewModel: AuthViewModel = hiltViewModel() // Not needed here usually

    var startDestination by remember { mutableStateOf(Screen.Splash.route) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToAuth = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Auth.route) {
            AuthScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToAuth = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onNavigateToCategories = {
                    navController.navigate(Screen.Categories.route)
                },
                onNavigateToBudgets = {
                    navController.navigate(Screen.Budgets.route)
                },
                onNavigateToAnalytics = {
                    navController.navigate(Screen.Analytics.route)
                },
                // You will need to make sure DashboardScreen accepts this parameter:
                onNavigateToAddTransaction = {
                     navController.navigate(Screen.AddExpense.route)
                }
            )
        }

        // --- NEW: Add Expense Screen ---
        composable(Screen.AddExpense.route) {
            com.smartexpense.app.ui.screens.expense.AddExpenseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Categories.route) {
            com.smartexpense.app.ui.screens.category.CategoryManagementScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Budgets.route) {
            com.smartexpense.app.ui.screens.budget.BudgetManagementScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Analytics.route) {
            // FIXED: Removed onNavigateBack because AnalyticsScreen doesn't take it
            com.smartexpense.app.ui.screens.analytics.AnalyticsScreen()
        }
    }
}