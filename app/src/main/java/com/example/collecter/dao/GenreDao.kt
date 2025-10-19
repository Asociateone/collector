package com.example.collecter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.collecter.dataObjects.Genre

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    suspend fun get(): List<Genre>

    @Query("SELECT * FROM genres WHERE id = :id")
    suspend fun getById(id: Int): Genre?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(genres: List<Genre>): Unit
}
