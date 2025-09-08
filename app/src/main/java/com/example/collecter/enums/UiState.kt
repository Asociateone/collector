package com.example.collecter.enums

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String? = null, val throwable: Throwable? = null) : UiState<Nothing>
}
