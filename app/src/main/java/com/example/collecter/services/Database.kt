package com.example.collecter.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.collecter.dataObjects.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase()