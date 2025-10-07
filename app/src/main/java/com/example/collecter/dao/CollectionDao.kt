package com.example.collecter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.collecter.dataObjects.Collection

@Dao
interface CollectionDao {
    @Query("SELECT * FROM collections")
    suspend fun get(): List<Collection>

    @Query("SELECT * FROM collections WHERE id = :collectionId")
    suspend fun getById(collectionId: Int): Collection?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collections: List<Collection>): Unit

    @Query("DELETE FROM collections WHERE id = :collectionId")
    fun delete(collectionId: Int) {}
}