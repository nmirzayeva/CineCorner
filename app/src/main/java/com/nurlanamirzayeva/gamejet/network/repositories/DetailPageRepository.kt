package com.nurlanamirzayeva.gamejet.network.repositories

import com.nurlanamirzayeva.gamejet.model.CreditsResponse
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.model.ReviewsResponse
import com.nurlanamirzayeva.gamejet.model.SimilarMoviesResponse
import com.nurlanamirzayeva.gamejet.model.Videos
import com.nurlanamirzayeva.gamejet.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class DetailPageRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getDetails(movieId:Int): Response<DetailsResponse> = apiService.getDetails(movieId)
    suspend fun getVideos(movieId: Int):Response<Videos> = apiService.getVideos(movieId)
    suspend fun getCredits(movieId: Int):Response<CreditsResponse> =apiService.getCredits(movieId)
    suspend fun getSimilarMovies(movieId: Int):Response<SimilarMoviesResponse> =apiService.getSimilar(movieId)
    suspend fun getReviews(movieId: Int):Response<ReviewsResponse> =apiService.getReviews(movieId)
}