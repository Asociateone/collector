package com.example.collecter.ui.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.dataObjects.User
import com.example.collecter.enums.UiState
import com.example.collecter.repositories.AuthRepository
import com.example.collecter.services.PreferenceDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository, val dataStore: PreferenceDataStore): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<User?>>(UiState.Success(null))
    val uiState: StateFlow<UiState<User?>> = _uiState

    /**
     * Login
     */
    fun login(email: String, password: String) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
//            _uiState.value  = authRepository.signIn(email, password)
        }
    }

    fun signUp(email: String, username: String, password: String, passwordConfirmation: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
//            _uiState.value = authRepository.signUp(email, username, password, passwordConfirmation)
        }
    }

    /**
     * Get Token
     */
    fun getToken(): Flow<String?> {
        return dataStore.apiKey
    }
}