package com.example.collecter.services

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.collecter.dao.CollectionDao
import com.example.collecter.dao.GameDao
import com.example.collecter.dao.GenreDao
import com.example.collecter.dao.PlatformDao
import com.example.collecter.dao.ScreenshotDao
import com.example.collecter.dao.UserDao
import com.example.collecter.dataObjects.Collection
import com.example.collecter.dataObjects.Game
import com.example.collecter.dataObjects.Genre
import com.example.collecter.dataObjects.Platform
import com.example.collecter.dataObjects.Screenshot
import com.example.collecter.dataObjects.User

@Database(
    entities = [
        User::class,
        Collection::class,
        Game::class,
        Genre::class,
        Platform::class,
        Screenshot::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun collectionDao(): CollectionDao
    abstract fun gameDao(): GameDao
    abstract fun genreDao(): GenreDao
    abstract fun platformDao(): PlatformDao
    abstract fun screenshotDao(): ScreenshotDao
}