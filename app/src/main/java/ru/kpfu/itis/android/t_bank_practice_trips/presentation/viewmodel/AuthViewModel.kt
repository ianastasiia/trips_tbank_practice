package ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.data.auth.AuthManager
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthResponse
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.domain.repository.AuthRepository
import javax.inject.Inject

//sealed class AuthState {
//    object Unauthenticated : AuthState()
//    object Loading : AuthState()
//    data class Error(val message: String) : AuthState()
//    data class Authenticated(val token: String) : AuthState()
//}

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authManager: AuthManager,
) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<AuthResponse>>(Result.Loading)
    val loginState: StateFlow<Result<AuthResponse>> = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<Result<AuthResponse>>(Result.Loading)
    val registerState: StateFlow<Result<AuthResponse>> = _registerState.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

//    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
//    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkAuthState()
    }

    fun checkAuthState() {
        viewModelScope.launch {
            authManager.isAuthenticated().collect { isAuth ->
                _isAuthenticated.value = isAuth
            }
        }
    }

    fun login(request: AuthRequest) {
        viewModelScope.launch {
            _loginState.value = Result.Loading
            try {
                val response = authRepository.login(request)
                authManager.saveToken(response.token)
                _loginState.value = Result.Success(response)
            } catch (e: Exception) {
                _loginState.value = Result.Error(e.message ?: "Ошибка авторизации")
            }
        }
    }

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _registerState.value = Result.Loading
            try {
                val response = authRepository.register(request)
                authManager.saveToken(response.token)
                _registerState.value = Result.Success(response)
            } catch (e: Exception) {
                _registerState.value = Result.Error(e.message ?: "Ошибка регистрации")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authManager.clearToken()
        }
    }


}