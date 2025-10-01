package com.example.collecter.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "collections")
data class Collection(
    @PrimaryKey
    val id: Int = 0,
    val title : String,
    val icon: String? = null
)
