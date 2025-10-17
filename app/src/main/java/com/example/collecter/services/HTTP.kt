package com.example.collecter.services

import android.util.Log
import com.example.collecter.dataObjects.ApiResource
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.Game
import com.example.collecter.dataObjects.PaginatedResponse
import com.example.collecter.dataObjects.User
import com.example.collecter.enums.DataStoreKeys
import com.example.collecter.enums.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import kotlin.collections.mapOf

@OptIn(InternalAPI::class)
class HTTP (val preferenceData: PreferenceDataStore) {
    val mainUrl = "https://collect.rainaldo.nl/api"

    private suspend fun getAuthHeader(): String {
        return "Bearer ${preferenceData.apiKey.first()}"
    }

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
            header("Authorization", getAuthHeader())
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        return response.body<UiState.Success<List<Collection>>>()
    }

    suspend fun getCollection(collectionId: Int): UiState<Collection> {
        val response = client.get("${mainUrl}/collections/${collectionId}") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        return response.body<UiState.Success<Collection>>()
    }

    suspend fun createCollection(title: String, icon: String? = null): UiState<Collection> {
        val body = mutableMapOf("title" to title)
        icon?.let { body["icon"] = it }

        val response = client.post("${mainUrl}/collections") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
            header("X-Device-Type", "mobile")
            setBody(body)
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        return response.body<UiState.Success<Collection>>()
    }

    suspend fun updateCollection(collectionId: Int, title: String?, icon: String?): UiState<Collection> {
        val body = mutableMapOf<String, String>()
        title?.let { body["title"] = it }
        icon?.let { body["icon"] = it }

        val response = client.patch("${mainUrl}/collections/${collectionId}") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
            header("X-Device-Type", "mobile")
            setBody(body)
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        return response.body<UiState.Success<Collection>>()
    }

    suspend fun deleteCollection(collectionId: Int): UiState<Unit> {
        val response = client.delete("${mainUrl}/collections/${collectionId}") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        return UiState.Success(Unit)
    }

    // Game endpoints
    suspend fun browseGames(
        search: String? = null,
        genre: Int? = null,
        platform: Int? = null,
        page: Int = 1
    ): UiState<PaginatedResponse<Game>> {
        var url = "${mainUrl}/games?page=${page}"
        search?.let { url += "&search=${it}" }
        genre?.let { url += "&genre=${it}" }
        platform?.let { url += "&platform=${it}" }

        val response = client.get(url) {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
            header("X-Device-Type", "mobile")
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        return UiState.Success(response.body<PaginatedResponse<Game>>())
    }

    suspend fun getGame(gameId: Int): UiState<Game> {
        val response = client.get("${mainUrl}/games/${gameId}") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
            header("X-Device-Type", "mobile")
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        val data = response.body<ApiResource<Game>>()
        return UiState.Success(data.data)
    }

    // Collection Game Management endpoints
    suspend fun getCollectionGames(collectionId: Int, status: String? = null): UiState<List<Game>> {
        var url = "${mainUrl}/collections/${collectionId}/games"
        status?.let { url += "?status=${it}" }

        val response = client.get(url) {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
            header("X-Device-Type", "mobile")
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        val data = response.body<ApiResource<List<Game>>>()
        return UiState.Success(data.data)
    }

    suspend fun addGameToCollection(collectionId: Int, gameId: Int, status: String = "wanted"): UiState<Game> {
        val response = client.post("${mainUrl}/collections/${collectionId}/games") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
            header("X-Device-Type", "mobile")
            setBody(mapOf(
                "game_id" to gameId,
                "status" to status
            ))
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        val data = response.body<ApiResource<Game>>()
        return UiState.Success(data.data)
    }

    suspend fun updateGameStatus(collectionId: Int, gameId: Int, status: String): UiState<Game> {
        val response = client.patch("${mainUrl}/collections/${collectionId}/games/${gameId}") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
            header("X-Device-Type", "mobile")
            setBody(mapOf("status" to status))
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        val data = response.body<ApiResource<Game>>()
        return UiState.Success(data.data)
    }

    suspend fun removeGameFromCollection(collectionId: Int, gameId: Int): UiState<Unit> {
        val response = client.delete("${mainUrl}/collections/${collectionId}/games/${gameId}") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        return UiState.Success(Unit)
    }

    // Genre endpoints
    suspend fun getGenres(): UiState<List<com.example.collecter.dataObjects.Genre>> {
        val response = client.get("${mainUrl}/genres") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        val data = response.body<ApiResource<List<com.example.collecter.dataObjects.Genre>>>()
        return UiState.Success(data.data)
    }

    // Platform endpoints
    suspend fun getPlatforms(): UiState<List<com.example.collecter.dataObjects.Platform>> {
        val response = client.get("${mainUrl}/platforms") {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            header("Authorization", getAuthHeader())
        }

        if (response.status.value >= 400) {
            return response.body<UiState.Error>()
        }

        val data = response.body<ApiResource<List<com.example.collecter.dataObjects.Platform>>>()
        return UiState.Success(data.data)
    }
}