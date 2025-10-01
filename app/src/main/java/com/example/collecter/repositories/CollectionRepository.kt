package com.example.collecter.repositories

import android.util.Log
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.User
import com.example.collecter.enums.DataStoreKeys
import com.example.collecter.enums.UiState
import com.example.collecter.services.Database
import com.example.collecter.services.HTTP
import com.example.collecter.services.PreferenceDataStore
import io.ktor.util.logging.Logger

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
}