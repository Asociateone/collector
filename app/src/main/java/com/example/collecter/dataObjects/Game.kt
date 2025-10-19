package com.example.collecter.dataObjects

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    val id: Int,
    @SerialName("igdb_id")
    val igdbId: Int,
    val title: String,
    val description: String? = null,
    @SerialName("cover_url")
    val coverUrl: String? = null,
    @SerialName("cover_urls")
    val coverUrls: ImageUrls? = null,
    val rating: Double? = null,
    val category: Int,
    @Ignore
    val genres: List<Genre> = emptyList(),
    @Ignore
    val platforms: List<Platform> = emptyList(),
    @Ignore
    val screenshots: List<Screenshot> = emptyList(),
    // Fields for games in collections
    @Ignore
    val status: String? = null,
    @SerialName("added_at")
    @Ignore
    val addedAt: String? = null
) {
    // Secondary constructor for Room (without @Ignore fields)
    constructor(
        id: Int,
        igdbId: Int,
        title: String,
        description: String?,
        coverUrl: String?,
        coverUrls: ImageUrls?,
        rating: Double?,
        category: Int
    ) : this(
        id = id,
        igdbId = igdbId,
        title = title,
        description = description,
        coverUrl = coverUrl,
        coverUrls = coverUrls,
        rating = rating,
        category = category,
        genres = emptyList(),
        platforms = emptyList(),
        screenshots = emptyList(),
        status = null,
        addedAt = null
    )
}
