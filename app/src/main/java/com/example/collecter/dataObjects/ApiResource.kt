package com.example.collecter.dataObjects

import kotlinx.serialization.Serializable

@Serializable
data class ApiResource<T>(
     val data: T
)
