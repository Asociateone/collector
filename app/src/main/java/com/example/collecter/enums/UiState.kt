package com.example.collecter.enums

import kotlinx.serialization.Serializable

@Serializable
sealed interface UiState<out T> {
    @Serializable
    data object Loading : UiState<Nothing>
    @Serializable
    data class Success<T>(val data: Nothing?) : UiState<T> // Changed Nothing? to T? to make it more generic and useful
    @Serializable
    data class Error(
        val message: String,
        val errors: List<List<String>>? = emptyList()
    ) : UiState<Nothing>
}
