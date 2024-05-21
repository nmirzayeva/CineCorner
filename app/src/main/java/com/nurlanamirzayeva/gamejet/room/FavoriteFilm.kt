package com.nurlanamirzayeva.gamejet.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "favorites")
data class FavoriteFilm(
    @field:SerializedName("id")
    @PrimaryKey val id: Int? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null

)