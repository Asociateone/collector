package com.example.collecter.services

import android.util.Log
import com.example.collecter.dataObjects.ApiFailedResponse
import com.example.collecter.dataObjects.ApiResource
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
import kotlinx.serialization.json.Json

@OptIn(InternalAPI::class)
class HTTP (val preferenceData: PreferenceDataStore) {
    val mainUrl = "https://collect.rainaldo.nl/api"

    private val client = HttpClient() {
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
        Log.d("HTTP", "Response: ${response.body<String>().toString()}")
    }

    /**
     * @param email
     * @param password
     */
    suspend fun signIn(email: String, password: String): UiState<Nothing> {
        val response = client.post("${mainUrl}/login") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            setBody(mapOf("email" to email, "password" to password))
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        if(response.status.value >= 200 && response.status.value <= 299) {
            val data = response.body<ApiResource<User>>()
            preferenceData.update(DataStoreKeys.API_KEY, data.data.token)
        }

        return UiState.Success(null)
    }
}