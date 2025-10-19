package com.example.collecter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.collecter.dataObjects.Platform

@Dao
interface PlatformDao {
    @Query("SELECT * FROM platforms")
    suspend fun get(): List<Platform>

    @Query("SELECT * FROM platforms WHERE id = :id")
    suspend fun getById(id: Int): Platform?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(platforms: List<Platform>): Unit
}
