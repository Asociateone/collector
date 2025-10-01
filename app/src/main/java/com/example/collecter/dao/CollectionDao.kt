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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(collections: List<Collection>): Unit
}