package com.nurlanamirzayeva.gamejet.network.repositories

import com.nurlanamirzayeva.gamejet.model.DiscoverResponse
import com.nurlanamirzayeva.gamejet.network.ApiService
import retrofit2.Response
import javax.inject.Inject


class MainPageRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getMovies(page:Int): Response<DiscoverResponse> = apiService.getMoviesByPage(page)
    suspend fun getTrendingNow(page:Int):Response<DiscoverResponse> =apiService.getTrendingNow(page)

}