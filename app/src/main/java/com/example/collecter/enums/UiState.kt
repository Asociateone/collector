package com.example.collecter.enums

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: Nothing?) : UiState<T>
    data object Error : UiState<Nothing>
}
