package com.example.collecter.dataObjects

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id : Int = 0,
    val name: String,
    val email: String,
    val token: String
)
