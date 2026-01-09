package com.smartexpense.app.data.repository;

import com.smartexpense.app.data.remote.ApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class TransactionRepository_Factory implements Factory<TransactionRepository> {
  private final Provider<ApiService> apiServiceProvider;

  public TransactionRepository_Factory(Provider<ApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public TransactionRepository get() {
    return newInstance(apiServiceProvider.get());
  }

  public static TransactionRepository_Factory create(Provider<ApiService> apiServiceProvider) {
    return new TransactionRepository_Factory(apiServiceProvider);
  }

  public static TransactionRepository newInstance(ApiService apiService) {
    return new TransactionRepository(apiService);
  }
}
