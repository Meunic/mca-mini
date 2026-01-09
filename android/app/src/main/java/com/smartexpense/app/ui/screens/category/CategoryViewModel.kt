package com.smartexpense.app.ui.screens.category

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartexpense.app.data.model.Category
import com.smartexpense.app.data.repository.CategoryRepository
import com.smartexpense.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    
    var state by mutableStateOf(CategoryState())
        private set
    
    init {
        loadCategories()
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            categoryRepository.syncCategories()
            categoryRepository.getCategories().collectLatest { categories ->
                state = state.copy(
                    categories = categories,
                    isLoading = false
                )
            }
        }
    }
    
    fun addCategory(name: String) {
        if (name.isBlank()) {
            state = state.copy(error = "Category name cannot be empty")
            return
        }
        
        viewModelScope.launch {
            state = state.copy(isAdding = true, error = null)
            when (val result = categoryRepository.addCategory(name)) {
                is Resource.Success -> {
                    state = state.copy(
                        isAdding = false,
                        newCategoryName = ""
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isAdding = false,
                        error = result.message
                    )
                }
                else -> {
                    state = state.copy(isAdding = false)
                }
            }
        }
    }
    
    fun deleteCategory(id: String) {
        viewModelScope.launch {
            state = state.copy(isDeleting = id)
            when (categoryRepository.deleteCategory(id)) {
                is Resource.Success -> {
                    state = state.copy(isDeleting = null)
                }
                is Resource.Error -> {
                    state = state.copy(
                        isDeleting = null,
                        error = "Failed to delete category"
                    )
                }
                else -> {
                    state = state.copy(isDeleting = null)
                }
            }
        }
    }
    
    fun updateNewCategoryName(name: String) {
        state = state.copy(newCategoryName = name, error = null)
    }
    
    fun refreshCategories() {
        loadCategories()
    }
}

data class CategoryState(
    val categories: List<Category> = emptyList(),
    val newCategoryName: String = "",
    val isLoading: Boolean = false,
    val isAdding: Boolean = false,
    val isDeleting: String? = null,
    val error: String? = null
)
