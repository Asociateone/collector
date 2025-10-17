package com.example.collecter.repositories

import com.example.collecter.dataObjects.Game
import com.example.collecter.dataObjects.PaginatedResponse
import com.example.collecter.enums.UiState
import com.example.collecter.services.Database
import com.example.collecter.services.HTTP

class GameRepository(val http: HTTP, val database: Database) {

    suspend fun browseGames(
        search: String? = null,
        genre: Int? = null,
        platform: Int? = null,
        page: Int = 1
    ): UiState<PaginatedResponse<Game>> {
        val result = http.browseGames(search, genre, platform, page)

        // Optionally cache games in local database
        if (result is UiState.Success) {
            database.gameDao().insert(result.data.data)
        }

        return result
    }

    suspend fun getGame(gameId: Int): UiState<Game> {
        // Try local database first
        val cached = database.gameDao().getById(gameId)
        if (cached != null) {
            return UiState.Success(cached)
        }

        // Fallback to network
        val result = http.getGame(gameId)
        if (result is UiState.Success) {
            database.gameDao().insert(listOf(result.data))
        }
        return result
    }
}
