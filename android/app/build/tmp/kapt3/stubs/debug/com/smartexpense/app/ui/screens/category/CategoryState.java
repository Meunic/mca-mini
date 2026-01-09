package com.smartexpense.app.ui.screens.category;

import androidx.lifecycle.ViewModel;
import com.smartexpense.app.data.model.Category;
import com.smartexpense.app.data.repository.CategoryRepository;
import com.smartexpense.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\fJ\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0016\u001a\u00020\bH\u00c6\u0003J\u000b\u0010\u0017\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003JO\u0010\u0019\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\b2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0006H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0011R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010\u00a8\u0006\u001f"}, d2 = {"Lcom/smartexpense/app/ui/screens/category/CategoryState;", "", "categories", "", "Lcom/smartexpense/app/data/model/Category;", "newCategoryName", "", "isLoading", "", "isAdding", "isDeleting", "error", "(Ljava/util/List;Ljava/lang/String;ZZLjava/lang/String;Ljava/lang/String;)V", "getCategories", "()Ljava/util/List;", "getError", "()Ljava/lang/String;", "()Z", "getNewCategoryName", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class CategoryState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.smartexpense.app.data.model.Category> categories = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String newCategoryName = null;
    private final boolean isLoading = false;
    private final boolean isAdding = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String isDeleting = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public CategoryState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.smartexpense.app.data.model.Category> categories, @org.jetbrains.annotations.NotNull()
    java.lang.String newCategoryName, boolean isLoading, boolean isAdding, @org.jetbrains.annotations.Nullable()
    java.lang.String isDeleting, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.smartexpense.app.data.model.Category> getCategories() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNewCategoryName() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isAdding() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String isDeleting() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public CategoryState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.smartexpense.app.data.model.Category> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.smartexpense.app.ui.screens.category.CategoryState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.smartexpense.app.data.model.Category> categories, @org.jetbrains.annotations.NotNull()
    java.lang.String newCategoryName, boolean isLoading, boolean isAdding, @org.jetbrains.annotations.Nullable()
    java.lang.String isDeleting, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}