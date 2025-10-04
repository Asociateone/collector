package com.example.collecter.repositories

import com.example.collecter.dataObjects.Collection
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

    suspend fun createCollection(title: String): UiState<Collection> {
        val result = http.createCollection(title)

        if (result is UiState.Success) {
            database.collectionDao().insert(listOf(result.data))
        }

        return result
    }
}