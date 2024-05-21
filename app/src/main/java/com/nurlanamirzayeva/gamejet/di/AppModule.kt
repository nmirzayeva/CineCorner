package com.nurlanamirzayeva.gamejet.di

import android.content.Context
import androidx.room.Room
import com.nurlanamirzayeva.gamejet.room.AppDatabase
import com.nurlanamirzayeva.gamejet.room.FavoriteFilmDao
import com.nurlanamirzayeva.gamejet.utils.MovieSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieSharedPreferences(@ApplicationContext context:Context):MovieSharedPreferences{
        return MovieSharedPreferences(context)

    }

    @Provides
    @Singleton
    fun provideFavoriteDatabase(@ApplicationContext context:Context): AppDatabase = Room.databaseBuilder(context,
        AppDatabase::class.java,"app_database").build()

    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteFilmDao = appDatabase.favoriteFilmDao()

}