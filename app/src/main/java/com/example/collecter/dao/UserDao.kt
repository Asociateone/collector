package com.example.collecter.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.collecter.dataObjects.User
import kotlinx.coroutines.flow.Flow

interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE token = :token Limit 1")
    suspend fun getUserByToken(token: String): Flow<User>
}