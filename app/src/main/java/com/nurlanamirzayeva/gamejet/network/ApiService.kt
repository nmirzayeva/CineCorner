package com.nurlanamirzayeva.gamejet.network

import com.nurlanamirzayeva.gamejet.model.CreditsResponse
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.model.DiscoverResponse
import com.nurlanamirzayeva.gamejet.model.Videos
import com.nurlanamirzayeva.gamejet.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
   @GET("discover/movie?api_key=$API_KEY")
   suspend fun getMoviesByPage(@Query("page")page:Int =1):Response<DiscoverResponse>

   @GET("trending/movie/day?api_key=$API_KEY")
   suspend fun getTrendingNow(@Query("page")page:Int=1):Response<DiscoverResponse>

   @GET("movie/{movie_id}?api_key=$API_KEY")
   suspend fun getDetails(
      @Path("movie_id") movieId:Int
   ):Response<DetailsResponse>

   @GET("movie/{movie_id}/videos?api_key=$API_KEY")
   suspend fun getVideos(
      @Path("movie_id") movieId: Int
   ):Response<Videos>

   @GET("movie/{movie_id}/credits?api_key=$API_KEY")
   suspend fun getCredits(
      @Path("movie_id") movieId: Int
   ):Response<CreditsResponse>

}