package com.example.collecter.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "screenshots")
data class Screenshot(
    @PrimaryKey
    val id: Int,
    val url: String,
    val urls: ImageUrls
)
