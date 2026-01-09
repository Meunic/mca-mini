package com.smartexpense.app.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartexpense.app.data.repository.AuthRepository
import com.smartexpense.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    var authState by mutableStateOf<AuthState>(AuthState.Idle)
        private set
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            when (val result = authRepository.login(email, password)) {
                is Resource.Success -> {
                    authState = AuthState.Success
                }
                is Resource.Error -> {
                    authState = AuthState.Error(result.message)
                }
                else -> {}
            }
        }
    }
    
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            authState = AuthState.Loading
            when (val result = authRepository.register(name, email, password)) {
                is Resource.Success -> {
                    authState = AuthState.Success
                }
                is Resource.Error -> {
                    authState = AuthState.Error(result.message)
                }
                else -> {}
            }
        }
    }
    
    suspend fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }
    
    fun resetState() {
        authState = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}
