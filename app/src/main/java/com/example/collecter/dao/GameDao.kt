package com.example.collecter.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.collecter.dataObjects.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM games")
    suspend fun get(): List<Game>

    @Query("SELECT * FROM games WHERE id = :id")
    suspend fun getById(id: Int): Game?

    @Query("SELECT * FROM games WHERE title LIKE '%' || :search || '%'")
    suspend fun search(search: String): List<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(games: List<Game>): Unit

    @Query("DELETE FROM games WHERE id = :id")
    suspend fun deleteById(id: Int): Unit
}
