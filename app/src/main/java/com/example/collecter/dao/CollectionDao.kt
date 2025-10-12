package com.example.collecter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    suspend fun insert(collections: List<Collection>): Unit

    @Query("DELETE FROM collections WHERE id = :collectionId")
    suspend fun delete(collectionId: Int)
}