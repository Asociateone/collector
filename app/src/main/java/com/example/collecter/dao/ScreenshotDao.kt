package com.example.collecter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.collecter.dataObjects.Screenshot

@Dao
interface ScreenshotDao {
    @Query("SELECT * FROM screenshots")
    suspend fun get(): List<Screenshot>

    @Query("SELECT * FROM screenshots WHERE id = :id")
    suspend fun getById(id: Int): Screenshot?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(screenshots: List<Screenshot>): Unit
}
