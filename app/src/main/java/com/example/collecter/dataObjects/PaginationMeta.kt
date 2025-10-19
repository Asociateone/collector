package com.example.collecter.dataObjects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationLinks(
    val first: String?,
    val last: String?,
    val prev: String?,
    val next: String?
)

@Serializable
data class PaginationMeta(
    @SerialName("current_page")
    val currentPage: Int,
    val from: Int?,
    @SerialName("last_page")
    val lastPage: Int,
    @SerialName("per_page")
    val perPage: Int,
    val to: Int?,
    val total: Int
)

@Serializable
data class PaginatedResponse<T>(
    val data: List<T>,
    val links: PaginationLinks,
    val meta: PaginationMeta
)
