package com.example.collecter.repositories

import com.example.collecter.enums.UiState
import com.example.collecter.services.HTTP

class AuthRepository(val http: HTTP)
{
    /**
     * @param email
     */
    suspend fun signIn(email: String, password: String): UiState<Nothing>
    {
        return http.signIn(email, password)
    }

    suspend fun signUp(email: String, username: String, password: String, passwordConfirmation: String): UiState<Nothing> {
        return http.signUp(email, username, password, passwordConfirmation)
    }
}