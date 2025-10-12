package com.example.collecter.repositories

import com.example.collecter.dataObjects.Collection
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
            database.collectionDao().insert(collections.data)
            return WebState.Success(Unit)
        }

        return WebState.Error(collections.toString())
    }

    suspend fun createCollection(title: String): WebState<Collection> {
        val result = http.createCollection(title)

        if (result is WebState.Success) {
            database.collectionDao().insert(listOf(result.data))
        }

        return result
    }

    suspend fun deleteCollection(collectionId: Int): WebState<Unit> {
        val result = http.deleteCollection(collectionId)

        if (result is WebState.Success) {
            database.collectionDao().delete(collectionId)
        }

        return result
    }
}