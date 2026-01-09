package com.smartexpense.app.ui.screens.category;

import androidx.lifecycle.ViewModel;
import com.smartexpense.app.data.model.Category;
import com.smartexpense.app.data.repository.CategoryRepository;
import com.smartexpense.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u0011J\b\u0010\u0014\u001a\u00020\u000fH\u0002J\u0006\u0010\u0015\u001a\u00020\u000fJ\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R+\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068F@BX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\f\u0010\r\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b\u00a8\u0006\u0017"}, d2 = {"Lcom/smartexpense/app/ui/screens/category/CategoryViewModel;", "Landroidx/lifecycle/ViewModel;", "categoryRepository", "Lcom/smartexpense/app/data/repository/CategoryRepository;", "(Lcom/smartexpense/app/data/repository/CategoryRepository;)V", "<set-?>", "Lcom/smartexpense/app/ui/screens/category/CategoryState;", "state", "getState", "()Lcom/smartexpense/app/ui/screens/category/CategoryState;", "setState", "(Lcom/smartexpense/app/ui/screens/category/CategoryState;)V", "state$delegate", "Landroidx/compose/runtime/MutableState;", "addCategory", "", "name", "", "deleteCategory", "id", "loadCategories", "refreshCategories", "updateNewCategoryName", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CategoryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.smartexpense.app.data.repository.CategoryRepository categoryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.compose.runtime.MutableState state$delegate = null;
    
    @javax.inject.Inject()
    public CategoryViewModel(@org.jetbrains.annotations.NotNull()
    com.smartexpense.app.data.repository.CategoryRepository categoryRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.ui.screens.category.CategoryState getState() {
        return null;
    }
    
    private final void setState(com.smartexpense.app.ui.screens.category.CategoryState p0) {
    }
    
    private final void loadCategories() {
    }
    
    public final void addCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void deleteCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void updateNewCategoryName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void refreshCategories() {
    }
}