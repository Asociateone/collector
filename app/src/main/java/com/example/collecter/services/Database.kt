package com.example.collecter.services

import androidx.room.Database // Annotation
import androidx.room.RoomDatabase
import com.example.collecter.dataObjects.User
import com.example.collecter.database.dao.UserDao

@Database(version = 1, entities = [User::class], exportSchema = true) // Enabled schema export
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
}