package com.example.collecter.ui.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collecter.repositories.AuthRepository
import com.example.collecter.ui.dataObjects.AuthObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository): ViewModel() {

    private val _uiState = MutableStateFlow(AuthObject("", ""))
    val uiState: StateFlow<AuthObject> = _uiState

    /**
     * Set Email
     */
    fun setEmail(email: String): Unit
    {
        _uiState.value = _uiState.value.copy(email = email)
    }

    /**
     * Set Password
     */
    fun setPassword(password: String): Unit
    {
        _uiState.value = _uiState.value.copy(password = password)
    }

    /**
     * Login
     */
    fun login(): Unit
    {
        viewModelScope.launch {
            repository.signIn(_uiState.value.email)
        }
    }
}