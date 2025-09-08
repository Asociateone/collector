package com.example.collecter.services

import android.util.Log
import com.example.collecter.dataObjects.ApiResource
import com.example.collecter.dataObjects.User
import com.example.collecter.enums.DataStoreKeys
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
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
    }

    suspend fun check() {
        val response = client.get("${mainUrl}/up")
        Log.d("HTTP", "Response: ${response.body<String>().toString()}")
    }

    suspend fun signIn(email: String, password: String) {
        val response = client.post("${mainUrl}/login") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            setBody(mapOf("email" to email, "password" to password))
        }

        val data = response.body<ApiResource<User>>()

        preferenceData.update(DataStoreKeys.API_KEY, data.data.token)
    }
}