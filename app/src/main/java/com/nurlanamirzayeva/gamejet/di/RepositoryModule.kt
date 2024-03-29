package com.nurlanamirzayeva.gamejet.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nurlanamirzayeva.gamejet.network.repositories.SignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(auth: FirebaseAuth,fireStore: FirebaseFirestore):SignUpRepository{
        return SignUpRepository(auth,fireStore)
    }





}