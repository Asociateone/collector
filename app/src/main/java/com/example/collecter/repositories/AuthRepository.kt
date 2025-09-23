package com.example.collecter.repositories

import com.example.collecter.dataObjects.User
import com.example.collecter.enums.UiState
import com.example.collecter.services.Database
import com.example.collecter.services.HTTP

class AuthRepository(val http: HTTP, val database: Database)
{
    /**
     * @param email
     */
    suspend fun signIn(email: String, password: String): UiState<User>
    {
        val data =  http.signIn(email, password)

        if (data is UiState.Success) {
            database.userDao().insertUser(data.data)
        }

        return data
    }

    suspend fun signUp(email: String, username: String, password: String, passwordConfirmation: String): UiState<User> {
        val response = http.signUp(email, username, password, passwordConfirmation)

        if (response is UiState.Success) {
            database.userDao().insertUser(response.data)
        }

        return response
    }
}