package com.example.collecter.enums

import kotlinx.serialization.Serializable

@Serializable
sealed interface UiState<out T> {
    @Serializable
    data object Loading : UiState<Nothing>
    @Serializable
    data class Success<T>(val data: T) : UiState<T>
    @Serializable
    data class Error(
        val message: String,
        val errors: Map<String, List<String>>? = emptyMap()
    ) : UiState<Nothing>
}
