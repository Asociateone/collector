package com.example.collecter.ui.models

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.enums.UiState
import com.example.collecter.repositories.AuthRepository
import com.example.collecter.services.PreferenceDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository, val dataStore: PreferenceDataStore): ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Nothing>>(UiState.Success(null))
    val uiState: StateFlow<UiState<Nothing>> = _uiState

    /**
     * Login
     */
    fun login(email: String, password: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            _uiState.value  = authRepository.signIn(email, password)
        }
    }

    /**
     * Get Token
     */
    suspend fun getToken(): String? {
        return dataStore.apiKey.first()
    }
}