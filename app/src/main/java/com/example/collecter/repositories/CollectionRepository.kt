package com.example.collecter.repositories

import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.Game
import com.example.collecter.enums.UiState
import com.example.collecter.services.Database
import com.example.collecter.services.HTTP

class CollectionRepository(val http: HTTP, val database: Database)
{
    suspend fun getCollections(): UiState<List<Collection>>
    {
        val collections =  http.getCollectionList()

        if (collections is UiState.Success) {
            database.collectionDao().insert(collections.data)
        }

        return collections
    }

    suspend fun getCollection(collectionId: Int): UiState<Collection> {
        // Try local database first
        val cached = database.collectionDao().getById(collectionId)
        if (cached != null) {
            return UiState.Success(cached)
        }

        // Fallback to network
        val result = http.getCollection(collectionId)
        if (result is UiState.Success) {
            database.collectionDao().insert(listOf(result.data))
        }
        return result
    }

    suspend fun createCollection(title: String, icon: String? = null): UiState<Collection> {
        val result = http.createCollection(title, icon)

        if (result is UiState.Success) {
            database.collectionDao().insert(listOf(result.data))
        }

        return result
    }

    suspend fun updateCollection(collectionId: Int, title: String?, icon: String?): UiState<Collection> {
        val result = http.updateCollection(collectionId, title, icon)

        if (result is UiState.Success) {
            database.collectionDao().insert(listOf(result.data))
        }

        return result
    }

    suspend fun deleteCollection(collectionId: Int): UiState<Unit> {
        val result = http.deleteCollection(collectionId)

        if (result is UiState.Success) {
            database.collectionDao().deleteById(collectionId)
        }

        return result
    }

    // Collection Game Management
    suspend fun getCollectionGames(collectionId: Int, status: String? = null): UiState<List<Game>> {
        return http.getCollectionGames(collectionId, status)
    }

    suspend fun addGameToCollection(collectionId: Int, gameId: Int, status: String = "wanted"): UiState<Game> {
        return http.addGameToCollection(collectionId, gameId, status)
    }

    suspend fun updateGameStatus(collectionId: Int, gameId: Int, status: String): UiState<Game> {
        return http.updateGameStatus(collectionId, gameId, status)
    }

    suspend fun removeGameFromCollection(collectionId: Int, gameId: Int): UiState<Unit> {
        return http.removeGameFromCollection(collectionId, gameId)
    }
}