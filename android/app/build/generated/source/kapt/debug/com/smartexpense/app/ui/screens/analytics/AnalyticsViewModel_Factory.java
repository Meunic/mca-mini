package com.smartexpense.app.ui.screens.analytics;

import com.smartexpense.app.data.repository.TransactionRepository;
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
public final class AnalyticsViewModel_Factory implements Factory<AnalyticsViewModel> {
  private final Provider<TransactionRepository> repositoryProvider;

  public AnalyticsViewModel_Factory(Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AnalyticsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static AnalyticsViewModel_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new AnalyticsViewModel_Factory(repositoryProvider);
  }

  public static AnalyticsViewModel newInstance(TransactionRepository repository) {
    return new AnalyticsViewModel(repository);
  }
}
