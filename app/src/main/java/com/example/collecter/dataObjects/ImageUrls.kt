package com.example.collecter.dataObjects

import kotlinx.serialization.Serializable

@Serializable
data class ImageUrls(
    val original: String,
    val medium: String,
    val thumb: String
)
