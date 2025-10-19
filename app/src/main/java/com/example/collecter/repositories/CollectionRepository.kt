package com.example.collecter.repositories

import android.util.Log
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.Game
import com.example.collecter.enums.UiState
import com.example.collecter.enums.WebState
import com.example.collecter.services.Database
import com.example.collecter.services.HTTP
import kotlinx.coroutines.flow.Flow

class CollectionRepository(val http: HTTP, val database: Database)
{
    fun getCollectionListFlow(): Flow<List<Collection>> {
        return database.collectionDao().getAll()
    }

    fun getCollectionFlow(collectionId: Int): Flow<Collection> {
        return database.collectionDao().getById(collectionId)
    }

    suspend fun syncCollections(): WebState<Unit>
    {
        val collections =  http.getCollectionList()

        if (collections is WebState.Success) {
            // Use replaceAll to delete items not in the server list
            database.collectionDao().replaceAll(collections.data)
            return WebState.Success(Unit)
        }

        return WebState.Error(collections.toString())
    }

    suspend fun createCollection(title: String, icon: String? = null): UiState<Collection> {
        val result = http.createCollection(title, icon)

        if (result is UiState.Success) {
            database.collectionDao().upsert(listOf(result.data))
        }

        return result
    }

    suspend fun deleteCollection(collectionId: Int): UiState<Unit> {
        Log.d("CollectionRepository", "Deleting collection with ID: $collectionId")
        val result = http.deleteCollection(collectionId)
        Log.d("CollectionRepository", "Delete result: $result")

        if (result is UiState.Success) {
            Log.d("CollectionRepository", "Deleting from database")
            database.collectionDao().delete(collectionId)
        }

        return result
    }

    suspend fun updateCollection(collectionId: Int, title: String?, icon: String?): UiState<Collection> {
        val result = http.updateCollection(collectionId, title, icon)

        if (result is UiState.Success) {
            database.collectionDao().upsert(listOf(result.data))
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