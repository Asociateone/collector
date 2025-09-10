package com.example.collecter.dataObjects

data class ApiFailedResponse(
    val message: String,
    val errors: List<List<String>>? = null
)
