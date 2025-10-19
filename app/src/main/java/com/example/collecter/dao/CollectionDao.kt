package com.example.collecter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.collecter.dataObjects.Collection
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {
    @Query("SELECT * FROM collections")
    suspend fun get(): List<Collection>

    @Query("SELECT * FROM collections")
    fun getAll(): Flow<List<Collection>>

    @Query("SELECT * FROM collections WHERE id = :collectionId")
    fun getById(collectionId: Int): Flow<Collection>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collections: List<Collection>)

    @Upsert
    suspend fun upsert(collections: List<Collection>)

    @Transaction
    suspend fun replaceAll(collections: List<Collection>) {
        deleteAll()
        insert(collections)
    }

    @Query("DELETE FROM collections")
    suspend fun deleteAll()

    @Query("DELETE FROM collections WHERE id = :collectionId")
    suspend fun delete(collectionId: Int)
}