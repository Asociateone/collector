package com.example.collecter.enums

import kotlinx.serialization.Serializable

@Serializable
sealed interface WebState<out T> {
    @Serializable
    data object Loading : WebState<Nothing>
    @Serializable
    data class Success<T>(val data: T) : WebState<T>
    @Serializable
    data class Error(
        val message: String,
        val errors: Map<String, List<String>>? = emptyMap()
    ) : WebState<Nothing>
}
