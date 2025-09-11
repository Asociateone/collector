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
}