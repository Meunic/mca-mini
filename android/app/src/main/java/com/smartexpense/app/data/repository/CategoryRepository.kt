package com.smartexpense.app.data.repository

import com.smartexpense.app.data.local.dao.CategoryDao
import com.smartexpense.app.data.model.AddCategoryRequest
import com.smartexpense.app.data.model.Category
import com.smartexpense.app.data.remote.ApiService
import com.smartexpense.app.util.NetworkHelper
import com.smartexpense.app.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val apiService: ApiService,
    private val categoryDao: CategoryDao,
    private val networkHelper: NetworkHelper
) {
    
    fun getCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }
    
    suspend fun syncCategories(): Resource<Unit> {
        return try {
            if (!networkHelper.isNetworkAvailable()) {
                return Resource.Error("No internet connection")
            }
            
            val response = apiService.getCategories()
            if (response.isSuccessful && response.body() != null) {
                val categories = response.body()!!.map { it.copy(synced = true) }
                categoryDao.insertAllCategories(categories)
                Resource.Success(Unit)
            } else {
                Resource.Error(response.message() ?: "Failed to fetch categories")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
    
    suspend fun addCategory(name: String): Resource<Category> {
        return try {
            if (networkHelper.isNetworkAvailable()) {
                val response = apiService.addCategory(AddCategoryRequest(name))
                if (response.isSuccessful && response.body() != null) {
                    val category = response.body()!!.copy(synced = true)
                    categoryDao.insertCategory(category)
                    Resource.Success(category)
                } else {
                    Resource.Error(response.message() ?: "Failed to add category")
                }
            } else {
                // Offline mode
                val category = Category(
                    id = "temp_${System.currentTimeMillis()}",
                    name = name,
                    synced = false
                )
                categoryDao.insertCategory(category)
                Resource.Success(category)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
    
    suspend fun deleteCategory(id: String): Resource<Unit> {
        return try {
            if (networkHelper.isNetworkAvailable()) {
                val response = apiService.deleteCategory(id)
                if (response.isSuccessful) {
                    categoryDao.deleteCategoryById(id)
                    Resource.Success(Unit)
                } else {
                    Resource.Error(response.message() ?: "Failed to delete category")
                }
            } else {
                categoryDao.deleteCategoryById(id)
                Resource.Success(Unit)
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An error occurred")
        }
    }
}
