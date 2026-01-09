package com.smartexpense.app.ui.navigation;

import androidx.compose.runtime.Composable;
import com.smartexpense.app.ui.screens.auth.AuthViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0007\u0007\b\t\n\u000b\f\rB\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001\u0007\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/smartexpense/app/ui/navigation/Screen;", "", "route", "", "(Ljava/lang/String;)V", "getRoute", "()Ljava/lang/String;", "AddExpense", "Analytics", "Auth", "Budgets", "Categories", "Dashboard", "Splash", "Lcom/smartexpense/app/ui/navigation/Screen$AddExpense;", "Lcom/smartexpense/app/ui/navigation/Screen$Analytics;", "Lcom/smartexpense/app/ui/navigation/Screen$Auth;", "Lcom/smartexpense/app/ui/navigation/Screen$Budgets;", "Lcom/smartexpense/app/ui/navigation/Screen$Categories;", "Lcom/smartexpense/app/ui/navigation/Screen$Dashboard;", "Lcom/smartexpense/app/ui/navigation/Screen$Splash;", "app_debug"})
public abstract class Screen {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String route = null;
    
    private Screen(java.lang.String route) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRoute() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/smartexpense/app/ui/navigation/Screen$AddExpense;", "Lcom/smartexpense/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class AddExpense extends com.smartexpense.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.smartexpense.app.ui.navigation.Screen.AddExpense INSTANCE = null;
        
        private AddExpense() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/smartexpense/app/ui/navigation/Screen$Analytics;", "Lcom/smartexpense/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Analytics extends com.smartexpense.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.smartexpense.app.ui.navigation.Screen.Analytics INSTANCE = null;
        
        private Analytics() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/smartexpense/app/ui/navigation/Screen$Auth;", "Lcom/smartexpense/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Auth extends com.smartexpense.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.smartexpense.app.ui.navigation.Screen.Auth INSTANCE = null;
        
        private Auth() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/smartexpense/app/ui/navigation/Screen$Budgets;", "Lcom/smartexpense/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Budgets extends com.smartexpense.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.smartexpense.app.ui.navigation.Screen.Budgets INSTANCE = null;
        
        private Budgets() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/smartexpense/app/ui/navigation/Screen$Categories;", "Lcom/smartexpense/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Categories extends com.smartexpense.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.smartexpense.app.ui.navigation.Screen.Categories INSTANCE = null;
        
        private Categories() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/smartexpense/app/ui/navigation/Screen$Dashboard;", "Lcom/smartexpense/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Dashboard extends com.smartexpense.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.smartexpense.app.ui.navigation.Screen.Dashboard INSTANCE = null;
        
        private Dashboard() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/smartexpense/app/ui/navigation/Screen$Splash;", "Lcom/smartexpense/app/ui/navigation/Screen;", "()V", "app_debug"})
    public static final class Splash extends com.smartexpense.app.ui.navigation.Screen {
        @org.jetbrains.annotations.NotNull()
        public static final com.smartexpense.app.ui.navigation.Screen.Splash INSTANCE = null;
        
        private Splash() {
        }
    }
}