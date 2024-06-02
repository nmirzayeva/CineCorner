package com.nurlanamirzayeva.gamejet.network

import com.nurlanamirzayeva.gamejet.model.ActorDetailResponse
import com.nurlanamirzayeva.gamejet.model.ActorImageResponse
import com.nurlanamirzayeva.gamejet.model.ActorMoviesResponse
import com.nurlanamirzayeva.gamejet.model.CreditsResponse
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.model.DiscoverResponse
import com.nurlanamirzayeva.gamejet.model.ReviewsResponse
import com.nurlanamirzayeva.gamejet.model.SimilarMoviesResponse
import com.nurlanamirzayeva.gamejet.model.TrendingResponse
import com.nurlanamirzayeva.gamejet.model.UpcomingResponse
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
   suspend fun getTrendingNow(@Query("page")page:Int=1):Response<TrendingResponse>

   @GET("movie/upcoming?api_key=$API_KEY")
   suspend fun getUpcomingMovies(@Query("page")page:Int=1):Response<UpcomingResponse>

   @GET("search/movie?api_key=$API_KEY")
   suspend fun getSearchMovies(@Query("page")page:Int=1,@Query("query") query: String):Response<DiscoverResponse>


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

   @GET("movie/{movie_id}/similar?api_key=$API_KEY")
   suspend fun getSimilar(
      @Path("movie_id") movieId: Int
   ):Response<SimilarMoviesResponse>

   @GET("movie/{movie_id}/reviews?api_key=$API_KEY")
suspend fun getReviews(
   @Path("movie_id") movieId: Int
):Response<ReviewsResponse>

  @GET("person/{person_id}?api_key=$API_KEY")
  suspend fun getActorDetails(
     @Path("person_id") personId:Int
  ):Response<ActorDetailResponse>

   @GET("person/{person_id}/images?api_key=$API_KEY")
   suspend fun getActorImages(
      @Path("person_id") personId:Int
   ):Response<ActorImageResponse>
   @GET("person/{person_id}/movie_credits?api_key=$API_KEY")
   suspend fun getActorMovies(
      @Path("person_id") personId:Int
   ):Response<ActorMoviesResponse>


}