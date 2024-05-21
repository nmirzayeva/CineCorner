package com.nurlanamirzayeva.gamejet.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nurlanamirzayeva.gamejet.BuildConfig
import com.nurlanamirzayeva.gamejet.network.ApiService
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import com.nurlanamirzayeva.gamejet.network.repositories.Repository
import com.nurlanamirzayeva.gamejet.room.FavoriteFilmDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(auth: FirebaseAuth,fireStore: FirebaseFirestore):Repository{
        return Repository(auth,fireStore)
    }

    @Provides
    @Singleton
    fun provideMainPageRepository(apiService: ApiService,fireStore: FirebaseFirestore,favoriteFilmDao: FavoriteFilmDao,auth: FirebaseAuth):MainPageRepository{
        return  MainPageRepository(apiService,fireStore,auth,favoriteFilmDao)
    }

}