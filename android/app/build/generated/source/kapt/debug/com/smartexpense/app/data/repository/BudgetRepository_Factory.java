package com.smartexpense.app.data.repository;

import com.smartexpense.app.data.local.dao.BudgetDao;
import com.smartexpense.app.data.remote.ApiService;
import com.smartexpense.app.util.NetworkHelper;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class BudgetRepository_Factory implements Factory<BudgetRepository> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<BudgetDao> budgetDaoProvider;

  private final Provider<NetworkHelper> networkHelperProvider;

  public BudgetRepository_Factory(Provider<ApiService> apiServiceProvider,
      Provider<BudgetDao> budgetDaoProvider, Provider<NetworkHelper> networkHelperProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.budgetDaoProvider = budgetDaoProvider;
    this.networkHelperProvider = networkHelperProvider;
  }

  @Override
  public BudgetRepository get() {
    return newInstance(apiServiceProvider.get(), budgetDaoProvider.get(), networkHelperProvider.get());
  }

  public static BudgetRepository_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<BudgetDao> budgetDaoProvider, Provider<NetworkHelper> networkHelperProvider) {
    return new BudgetRepository_Factory(apiServiceProvider, budgetDaoProvider, networkHelperProvider);
  }

  public static BudgetRepository newInstance(ApiService apiService, BudgetDao budgetDao,
      NetworkHelper networkHelper) {
    return new BudgetRepository(apiService, budgetDao, networkHelper);
  }
}
