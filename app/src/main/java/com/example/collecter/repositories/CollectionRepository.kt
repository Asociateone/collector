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
        val collection = http.getCollection(collectionId)
        return collection
    }
}