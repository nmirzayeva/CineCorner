package com.nurlanamirzayeva.gamejet.network.repositories

import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.network.ApiService
import retrofit2.Response

class DetailPageRepository(private val apiService: ApiService) {
    suspend fun getDetails(movieId:Int): Response<DetailsResponse> = apiService.getDetails(movieId)

}