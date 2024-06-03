package com.nurlanamirzayeva.gamejet.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.checkerframework.common.value.qual.IntRangeFromNonNegative


@Dao
interface FavoriteFilmDao {
    @Query("select * from favorites where userId = :userId")
    fun getFavoritesFilms(userId:String): List<FavoriteFilm>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteFilm(film:FavoriteFilm)

    @Delete
    suspend fun deleteFavoriteFilm(film:FavoriteFilm)


}