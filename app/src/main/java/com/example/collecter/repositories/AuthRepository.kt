package com.example.collecter.repositories

import com.example.collecter.dataObjects.User
import com.example.collecter.enums.DataStoreKeys
import com.example.collecter.enums.UiState
import com.example.collecter.enums.WebState
import com.example.collecter.services.Database
import com.example.collecter.services.HTTP
import com.example.collecter.services.PreferenceDataStore

class AuthRepository(val http: HTTP, val database: Database, val preferenceData: PreferenceDataStore)
{
    /**
     * @param email
     */
    suspend fun signIn(email: String, password: String): WebState<User>
    {
        val data =  http.signIn(email, password)

        if (data is WebState.Success) {
            database.userDao().insertUser(data.data)
            preferenceData.update(DataStoreKeys.API_KEY, data.data.token)
        }

        return data
    }

    suspend fun signUp(email: String, username: String, password: String, passwordConfirmation: String): WebState<User> {
        val response = http.signUp(email, username, password, passwordConfirmation)

        if (response is WebState.Success) {
            database.userDao().insertUser(response.data)
            preferenceData.update(DataStoreKeys.API_KEY, response.data.token)
        }

        return response
    }

    /**
     * Get the currently authenticated user's information
     */
    suspend fun getCurrentUser(): WebState<User> {
        val response = http.getCurrentUser()

        // Don't update database - getCurrentUser doesn't return token,
        // and we don't want to overwrite the stored user with empty token

        return response
    }

    /**
     * Delete the currently authenticated user's account
     */
    suspend fun deleteAccount(): WebState<Boolean> {
        val response = http.deleteAccount()

        if (response is WebState.Success) {
            // Clear user data from local database
            database.userDao().deleteAll()
            // Clear API key
            preferenceData.update(DataStoreKeys.API_KEY, "")
        }

        return response
    }
}