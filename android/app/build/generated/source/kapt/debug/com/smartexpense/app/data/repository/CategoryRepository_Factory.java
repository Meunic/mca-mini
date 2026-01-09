package com.smartexpense.app.data.repository;

import com.smartexpense.app.data.local.dao.CategoryDao;
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
public final class CategoryRepository_Factory implements Factory<CategoryRepository> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<CategoryDao> categoryDaoProvider;

  private final Provider<NetworkHelper> networkHelperProvider;

  public CategoryRepository_Factory(Provider<ApiService> apiServiceProvider,
      Provider<CategoryDao> categoryDaoProvider, Provider<NetworkHelper> networkHelperProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.categoryDaoProvider = categoryDaoProvider;
    this.networkHelperProvider = networkHelperProvider;
  }

  @Override
  public CategoryRepository get() {
    return newInstance(apiServiceProvider.get(), categoryDaoProvider.get(), networkHelperProvider.get());
  }

  public static CategoryRepository_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<CategoryDao> categoryDaoProvider, Provider<NetworkHelper> networkHelperProvider) {
    return new CategoryRepository_Factory(apiServiceProvider, categoryDaoProvider, networkHelperProvider);
  }

  public static CategoryRepository newInstance(ApiService apiService, CategoryDao categoryDao,
      NetworkHelper networkHelper) {
    return new CategoryRepository(apiService, categoryDao, networkHelper);
  }
}
