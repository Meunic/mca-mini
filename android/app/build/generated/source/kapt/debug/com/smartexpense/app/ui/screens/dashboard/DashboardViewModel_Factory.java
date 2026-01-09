package com.smartexpense.app.ui.screens.dashboard;

import com.smartexpense.app.data.repository.AuthRepository;
import com.smartexpense.app.data.repository.TransactionRepository;
import com.smartexpense.app.data.repository.WalletRepository;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<WalletRepository> walletRepositoryProvider;

  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public DashboardViewModel_Factory(Provider<WalletRepository> walletRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.walletRepositoryProvider = walletRepositoryProvider;
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(walletRepositoryProvider.get(), transactionRepositoryProvider.get(), authRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<WalletRepository> walletRepositoryProvider,
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new DashboardViewModel_Factory(walletRepositoryProvider, transactionRepositoryProvider, authRepositoryProvider);
  }

  public static DashboardViewModel newInstance(WalletRepository walletRepository,
      TransactionRepository transactionRepository, AuthRepository authRepository) {
    return new DashboardViewModel(walletRepository, transactionRepository, authRepository);
  }
}
