package com.example.collecter.services

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.collecter.dao.CollectionDao
import com.example.collecter.dao.UserDao
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.User

@Database(
    entities = [
        User::class,
        Collection::class
    ],
    version = 2,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun collectionDao(): CollectionDao
}