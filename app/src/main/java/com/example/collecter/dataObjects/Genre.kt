package com.example.collecter.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey
    val id: Int,
    val name: String,
    @SerialName("igdb_id")
    val igdbId: Int? = null
)
