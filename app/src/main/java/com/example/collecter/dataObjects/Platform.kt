package com.example.collecter.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "platforms")
data class Platform(
    @PrimaryKey
    val id: Int,
    val name: String,
    @SerialName("logo_url")
    val logoUrl: String? = null,
    @SerialName("logo_urls")
    val logoUrls: ImageUrls? = null
)
