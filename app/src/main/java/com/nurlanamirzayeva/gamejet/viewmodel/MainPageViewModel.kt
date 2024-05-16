package com.nurlanamirzayeva.gamejet.viewmodel

import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.MediaStore.Video
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.log
import com.nurlanamirzayeva.gamejet.model.CreditsResponse
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.paging.DiscoverPagingSource
import com.nurlanamirzayeva.gamejet.model.DiscoverResponse
import com.nurlanamirzayeva.gamejet.model.ResultsVideoItem
import com.nurlanamirzayeva.gamejet.model.UpcomingResponse
import com.nurlanamirzayeva.gamejet.model.Videos
import com.nurlanamirzayeva.gamejet.network.repositories.DetailPageRepository
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import com.nurlanamirzayeva.gamejet.paging.TrendingPagingSource
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val mainPageRepository: MainPageRepository,
    private val detailPageRepository: DetailPageRepository
) : ViewModel() {

    private val _movieListResponse: MutableStateFlow<DiscoverResponse?> =
        MutableStateFlow(null)
    val movieListResponse = _movieListResponse.asStateFlow()

    private val _trendingMovieResponse: MutableStateFlow<DiscoverResponse?> = MutableStateFlow(null)
    val trendingMovieResponse = _trendingMovieResponse.asStateFlow()

    private val _upcomingMoviesResponse:MutableStateFlow<UpcomingResponse?> =MutableStateFlow(null)
    val upcomingMovieResponse=_upcomingMoviesResponse.asStateFlow()

    private val _detailPageResponse: MutableStateFlow<DetailsResponse?> = MutableStateFlow(null)
    val detailPageResponse = _detailPageResponse.asStateFlow()

    private val _videoListResponse:MutableStateFlow<NetworkState<Videos>?> = MutableStateFlow(null)
    val videoListResponse= _videoListResponse.asStateFlow()

    private val _creditListResponse:MutableStateFlow<CreditsResponse?> = MutableStateFlow(null)
    val creditListResponse=_creditListResponse.asStateFlow()


    var errorMessage: String by mutableStateOf("")

    var movieId = mutableIntStateOf(0 )



    fun getMovieList() {

        viewModelScope.launch(Dispatchers.IO) {


            try {

                mainPageRepository.getMovies(page = 1).also {

                    if (it.isSuccessful) {

                        _movieListResponse.value = it.body()


                    }

                }

            } catch (e: Exception) {

                errorMessage = e.message.toString()
            }

        }

    }

    fun getTrendingNow() {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                mainPageRepository.getTrendingNow(page = 1).also {
                    if (it.isSuccessful) {
                        _trendingMovieResponse.value = it.body()
                    }
                }
            } catch (e: Exception) {

                errorMessage = e.message.toString()
            }

        }
    }

    fun getUpcomingMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mainPageRepository.getUpcomingMovies(page = 1).also {
                    if(it.isSuccessful){
                        _upcomingMoviesResponse.value=it.body()
                    }
                }
            } catch (e:Exception){
                errorMessage=e.message.toString()
            }

        }
    }

    fun getDetails() {
        viewModelScope.launch(Dispatchers.IO) {

            try {
            val detailList= detailPageRepository.getDetails(movieId.intValue)
                _detailPageResponse.value=detailList.body()

            }
            catch (e:Exception){
                errorMessage= e.message.toString()
            }

        }

    }


    fun getVideos(){
        viewModelScope.launch(Dispatchers.IO) {

            _videoListResponse.value = NetworkState.Loading()
            try {
                val videoItems = detailPageRepository.getVideos(movieId.intValue)
                videoItems.body()?.let {
                    _videoListResponse.value= NetworkState.Success(data = it)
                }

            }
            catch (e:Exception){
                errorMessage=e.message.toString()
                _videoListResponse.value=NetworkState.Error(errorMessage = errorMessage)
            }
        }

    }

    fun getCredits(){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val creditItems= detailPageRepository.getCredits(movieId.intValue)
                _creditListResponse.value=creditItems.body()
            }
            catch(e:Exception){
                errorMessage=e.message.toString()
            }
        }
    }


    val discoverListPager = Pager(

        config = PagingConfig(pageSize = 20),
        initialKey = 1,
        pagingSourceFactory = {
            DiscoverPagingSource(
                mainPageRepository
            )
        }

    ).flow.cachedIn(viewModelScope)


    val trendingListPager = Pager(
        config = PagingConfig(pageSize = 20),
        initialKey = 1,
        pagingSourceFactory = {
            TrendingPagingSource(mainPageRepository)
        }
    ).flow.cachedIn(viewModelScope)

}