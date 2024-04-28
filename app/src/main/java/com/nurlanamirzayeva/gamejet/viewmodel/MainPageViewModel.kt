package com.nurlanamirzayeva.gamejet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.nurlanamirzayeva.gamejet.paging.DiscoverPagingSource
import com.nurlanamirzayeva.gamejet.model.DiscoverResponse
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import com.nurlanamirzayeva.gamejet.paging.TrendingPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainPageViewModel(private val mainPageRepository: MainPageRepository) : ViewModel() {

    private val _movieListResponse: MutableStateFlow<DiscoverResponse?> =
        MutableStateFlow(null)
    val movieListResponse = _movieListResponse.asStateFlow()

    private val _trendingMovieResponse: MutableStateFlow<DiscoverResponse?> = MutableStateFlow(null)
    val trendingMovieResponse = _trendingMovieResponse.asStateFlow()


    val firstDiscoverImage = mutableStateOf("")

    val firstTrendingImage = mutableStateOf("")


    var errorMessage: String by mutableStateOf("")


    fun getMovieList() {

        viewModelScope.launch(Dispatchers.IO) {


            try {

                mainPageRepository.getMovies(page = 1).also {

                    if (it.isSuccessful) {

                        _movieListResponse.value = it.body()

                        if (firstDiscoverImage.value.isEmpty()) {
                            firstDiscoverImage.value =
                                it.body()?.results!![2].backdropPath.toString()

                        }
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
                        if(firstTrendingImage.value.isEmpty()){

                            firstTrendingImage.value=it.body()?.results!![2].backdropPath.toString()
                        }

                    }


                }
            } catch (e: Exception) {

                errorMessage = e.message.toString()
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