package com.example.collecter.services

import android.util.Log
import com.example.collecter.dataObjects.ApiResource
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.User
import com.example.collecter.enums.DataStoreKeys
import com.example.collecter.enums.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

@OptIn(InternalAPI::class)
class HTTP (val preferenceData: PreferenceDataStore) {
    val mainUrl = "https://collect.rainaldo.nl/api"

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000L
            connectTimeoutMillis = 15000L
            socketTimeoutMillis = 15000L
        }
    }

    /**
     * check if everything is up
     */
    suspend fun check() {
        val response = client.get("${mainUrl}/up")
        Log.d("HTTP", "Response: ${response.body<String>()}")
    }

    /**
     * @param email
     * @param password
     */
    suspend fun signIn(email: String, password: String): UiState<User> {
        val response = client.post("${mainUrl}/login") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            setBody(mapOf("email" to email, "password" to password))
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        val data = response.body<ApiResource<User>>()
        return UiState.Success(data.data)
    }

    suspend fun signUp(
        email: String,
        username: String,
        password: String,
        passwordConfirmation: String
    ): UiState<User> {
        val response = client.post("${mainUrl}/signup") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            setBody(mapOf(
                "email" to email,
                "name" to username,
                "password" to password,
                "password_confirmation" to passwordConfirmation,
            ))
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        if(response.status.value >= 200 && response.status.value <= 299) {
            val data = response.body<ApiResource<User>>()
            preferenceData.update(DataStoreKeys.API_KEY, data.data.token)
        }

        return UiState.Success(response.body())
    }

    suspend fun getCollectionList(): UiState<List<Collection>> {
        val response = client.get("${mainUrl}/collections") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", "Bearer ${preferenceData.apiKey.first()}")
        }

//        if (response.status.value >= 400) {
//            return response.body<UiState.Error>()
//        }
//

        return response.body<UiState.Success<List<Collection>>>()
    }

    suspend fun getCollection(collectionId: Int): UiState<Collection> {
        val response = client.get("${mainUrl}/collections/${collectionId}") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", "Bearer ${preferenceData.apiKey.first()}")
        }

        return response.body<UiState.Success<Collection>>()
    }
}