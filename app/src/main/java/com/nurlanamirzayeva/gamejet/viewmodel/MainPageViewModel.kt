package com.nurlanamirzayeva.gamejet.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.model.CreditsResponse
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.paging.DiscoverPagingSource
import com.nurlanamirzayeva.gamejet.model.DiscoverResponse
import com.nurlanamirzayeva.gamejet.model.ProfileItemDTO
import com.nurlanamirzayeva.gamejet.model.ResultsItem
import com.nurlanamirzayeva.gamejet.model.SimilarMoviesResponse
import com.nurlanamirzayeva.gamejet.model.UpcomingResponse
import com.nurlanamirzayeva.gamejet.model.Videos
import com.nurlanamirzayeva.gamejet.network.repositories.DetailPageRepository
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import com.nurlanamirzayeva.gamejet.paging.SearchPagingSource
import com.nurlanamirzayeva.gamejet.paging.TrendingPagingSource
import com.nurlanamirzayeva.gamejet.room.FavoriteFilm
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val mainPageRepository: MainPageRepository,
    private val detailPageRepository: DetailPageRepository,
    private val auth: FirebaseAuth
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

    private val _similarListResponse:MutableStateFlow<SimilarMoviesResponse?> = MutableStateFlow(null)
    val similarListResponse=_similarListResponse.asStateFlow()

    private val _checkFavoriteResponse:MutableStateFlow<NetworkState<Boolean>?> = MutableStateFlow(null)
    val checkFavoriteResponse=_checkFavoriteResponse.asStateFlow()

    private val _removeFavoriteResponse:MutableStateFlow<NetworkState<Boolean>?> = MutableStateFlow(null)
    val removeFavoriteResponse=_removeFavoriteResponse.asStateFlow()


    var errorMessage: String by mutableStateOf("")

    var movieId = mutableIntStateOf(0 )

    private val _profileInfo:MutableStateFlow<NetworkState<ProfileItemDTO>?> = MutableStateFlow(null)
    val profileInfo=_profileInfo.asStateFlow()

    private val _filmDetails = MutableStateFlow<DetailsResponse?>(null)
    val filmDetails = _filmDetails.asStateFlow()

    private val _addFavoriteFilms=MutableStateFlow<NetworkState<Boolean>?>(null)
    val addFavoriteFilms = _addFavoriteFilms.asStateFlow()

    val userId= auth.currentUser?.uid ?: "unknown"

    var searchQuery = MutableStateFlow("")


    private val _searchResults = MutableStateFlow<PagingData<ResultsItem>>(PagingData.empty())
    val  searchResults:StateFlow<PagingData<ResultsItem>> =_searchResults.asStateFlow()


    @OptIn(FlowPreview::class)
    fun getSearchResults() = viewModelScope.launch(Dispatchers.IO) {
        searchQuery.debounce(1000L).collectLatest { searchTerm ->
            Pager(
                config = PagingConfig(pageSize = 20),
                initialKey = 1,
                pagingSourceFactory = {
                    SearchPagingSource(mainPageRepository = mainPageRepository, searchTerm = searchTerm)
                }
            ).flow
                .cachedIn(viewModelScope)
                .collectLatest { _searchResults.value = it }
        }
    }




    fun getFilmDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = detailPageRepository.getDetails(movieId.intValue)
            if (response.isSuccessful) {
                _filmDetails.value = response.body()
            }
        }
    }


    fun addFavorite(film: FavoriteFilm) {
        viewModelScope.launch(Dispatchers.IO) {
            mainPageRepository.addFavoriteFilms(film).collectLatest {state->
                _addFavoriteFilms.value=state

            }
        }
    }


    fun addFavoriteLocal(film: FavoriteFilm) {
        viewModelScope.launch(Dispatchers.IO) {
            mainPageRepository.addFavoriteLocal(film)
        }
    }

    fun removeFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
         mainPageRepository.removeFavoriteFilm(movieId.intValue).collectLatest {state->
             _removeFavoriteResponse.value=state
         }
        }
    }

    fun checkFavorite(){
        viewModelScope.launch(Dispatchers.IO) {
            mainPageRepository.checkFavoriteFilm(movieId.intValue).collectLatest {state->
                _checkFavoriteResponse.value=state

            }
        }

    }
    fun resetFavorite() {
        _checkFavoriteResponse.value = null
    }

    fun resetRemoveFavoriteState() {
        _removeFavoriteResponse.value = null
    }

    fun resetAddFavoriteState() {
        _addFavoriteFilms.value = null
    }

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

     fun fetchUserData(){
        viewModelScope.launch(Dispatchers.IO) {
            auth.currentUser?.uid?.let {uid->
                mainPageRepository.getUserData(uid).collectLatest{state->
                    if(uid.isNotEmpty()) {
                        _profileInfo.value=state
                    }
                }
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

    fun getSimilar(){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val similarItems= detailPageRepository.getSimilarMovies(movieId.intValue)
                _similarListResponse.value= similarItems.body()
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