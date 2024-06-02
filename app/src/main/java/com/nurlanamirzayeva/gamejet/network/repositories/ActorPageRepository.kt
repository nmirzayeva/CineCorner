package com.nurlanamirzayeva.gamejet.network.repositories

import com.nurlanamirzayeva.gamejet.model.ActorDetailResponse
import com.nurlanamirzayeva.gamejet.model.ActorImageResponse
import com.nurlanamirzayeva.gamejet.model.ActorMoviesResponse
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class ActorPageRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getActorDetails(personId:Int): Response<ActorDetailResponse> = apiService.getActorDetails(personId)
    suspend fun getActorImages(personId:Int): Response<ActorImageResponse> = apiService.getActorImages(personId)
    suspend fun getActorMovies(personId:Int): Response<ActorMoviesResponse> = apiService.getActorMovies(personId)

}