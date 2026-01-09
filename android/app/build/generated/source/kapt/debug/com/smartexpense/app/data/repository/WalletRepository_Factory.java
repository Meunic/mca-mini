package com.smartexpense.app.data.repository;

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
public final class WalletRepository_Factory implements Factory<WalletRepository> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<NetworkHelper> networkHelperProvider;

  public WalletRepository_Factory(Provider<ApiService> apiServiceProvider,
      Provider<NetworkHelper> networkHelperProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.networkHelperProvider = networkHelperProvider;
  }

  @Override
  public WalletRepository get() {
    return newInstance(apiServiceProvider.get(), networkHelperProvider.get());
  }

  public static WalletRepository_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<NetworkHelper> networkHelperProvider) {
    return new WalletRepository_Factory(apiServiceProvider, networkHelperProvider);
  }

  public static WalletRepository newInstance(ApiService apiService, NetworkHelper networkHelper) {
    return new WalletRepository(apiService, networkHelper);
  }
}
