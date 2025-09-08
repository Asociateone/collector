package com.example.collecter.dataObjects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val email: String,
    val token: String
)
