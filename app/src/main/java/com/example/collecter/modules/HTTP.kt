package com.example.collecter.modules

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.content
import io.ktor.client.statement.request
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HTTP {
    val mainUrl = "https://collect.rainaldo.nl/api"

    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true // Helpful for API changes
            })
        }
    }

    suspend fun check() {
        val response = client.get("${mainUrl}/up")
        Log.d("HTTP", "Response: ${response.status.value.toString()}") // Example usage
        Log.d("TAG", "?")
    }
}