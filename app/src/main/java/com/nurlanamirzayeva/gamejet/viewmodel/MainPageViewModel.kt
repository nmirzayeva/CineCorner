package com.nurlanamirzayeva.gamejet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nurlanamirzayeva.gamejet.Paging.MoviesPagingSource
import com.nurlanamirzayeva.gamejet.model.DiscoverResponse
import com.nurlanamirzayeva.gamejet.model.ResultsItem
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainPageViewModel(private val mainPageRepository: MainPageRepository) : ViewModel() {

    private val _movieListResponse: MutableStateFlow<DiscoverResponse?> =
        MutableStateFlow(null)

    val movieListResponse = _movieListResponse.asStateFlow()


    var errorMessage: String by mutableStateOf("")


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
                mainPageRepository.getTrendingNow().also {
                    if (it.isSuccessful) {
                        _movieListResponse.value = it.body()


                    }


                }
            } catch (e: Exception) {

                errorMessage = e.message.toString()
            }

        }


    }

    val movieListPager = Pager(

        config = PagingConfig(pageSize = 5),
        initialKey = 0,
        pagingSourceFactory = {
            MoviesPagingSource(
                mainPageRepository
            )


        }

    ).flow.cachedIn(viewModelScope)


}