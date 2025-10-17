package com.example.collecter.services

import androidx.room.TypeConverter
import com.example.collecter.dataObjects.ImageUrls
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun fromImageUrls(value: ImageUrls?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toImageUrls(value: String?): ImageUrls? {
        return value?.let { Json.decodeFromString(it) }
    }
}
