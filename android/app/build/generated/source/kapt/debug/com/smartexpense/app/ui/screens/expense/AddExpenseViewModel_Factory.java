package com.smartexpense.app.ui.screens.expense;

import com.smartexpense.app.data.repository.CategoryRepository;
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
public final class AddExpenseViewModel_Factory implements Factory<AddExpenseViewModel> {
  private final Provider<TransactionRepository> transactionRepositoryProvider;

  private final Provider<CategoryRepository> categoryRepositoryProvider;

  private final Provider<WalletRepository> walletRepositoryProvider;

  public AddExpenseViewModel_Factory(Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<WalletRepository> walletRepositoryProvider) {
    this.transactionRepositoryProvider = transactionRepositoryProvider;
    this.categoryRepositoryProvider = categoryRepositoryProvider;
    this.walletRepositoryProvider = walletRepositoryProvider;
  }

  @Override
  public AddExpenseViewModel get() {
    return newInstance(transactionRepositoryProvider.get(), categoryRepositoryProvider.get(), walletRepositoryProvider.get());
  }

  public static AddExpenseViewModel_Factory create(
      Provider<TransactionRepository> transactionRepositoryProvider,
      Provider<CategoryRepository> categoryRepositoryProvider,
      Provider<WalletRepository> walletRepositoryProvider) {
    return new AddExpenseViewModel_Factory(transactionRepositoryProvider, categoryRepositoryProvider, walletRepositoryProvider);
  }

  public static AddExpenseViewModel newInstance(TransactionRepository transactionRepository,
      CategoryRepository categoryRepository, WalletRepository walletRepository) {
    return new AddExpenseViewModel(transactionRepository, categoryRepository, walletRepository);
  }
}
