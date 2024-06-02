package com.nurlanamirzayeva.gamejet.viewmodel

import android.content.Context
import android.net.Uri
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
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.model.CreditsResponse
import com.nurlanamirzayeva.gamejet.model.DetailsResponse
import com.nurlanamirzayeva.gamejet.paging.DiscoverPagingSource
import com.nurlanamirzayeva.gamejet.model.DiscoverResponse
import com.nurlanamirzayeva.gamejet.model.ProfileItemDTO
import com.nurlanamirzayeva.gamejet.model.ResultsItem
import com.nurlanamirzayeva.gamejet.model.ReviewsResponse
import com.nurlanamirzayeva.gamejet.model.SimilarMoviesResponse
import com.nurlanamirzayeva.gamejet.model.TrendingResponse
import com.nurlanamirzayeva.gamejet.model.UpcomingResponse
import com.nurlanamirzayeva.gamejet.model.Videos
import com.nurlanamirzayeva.gamejet.network.repositories.DetailPageRepository
import com.nurlanamirzayeva.gamejet.network.repositories.MainPageRepository
import com.nurlanamirzayeva.gamejet.paging.SearchPagingSource
import com.nurlanamirzayeva.gamejet.paging.TrendingPagingSource
import com.nurlanamirzayeva.gamejet.paging.UpcomingPagingSource
import com.nurlanamirzayeva.gamejet.room.FavoriteFilm
import com.nurlanamirzayeva.gamejet.utils.CONFIRM_PASSWORD
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.utils.PASSWORD
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

    private val _trendingMovieResponse: MutableStateFlow<TrendingResponse?> = MutableStateFlow(null)
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

    private val _reviewListResponse:MutableStateFlow<ReviewsResponse?> = MutableStateFlow(null)
    val reviewListResponse=_reviewListResponse.asStateFlow()

    private val _checkFavoriteResponse:MutableStateFlow<NetworkState<Boolean>?> = MutableStateFlow(null)
    val checkFavoriteResponse=_checkFavoriteResponse.asStateFlow()

    private val _checkHistoryResponse:MutableStateFlow<NetworkState<Boolean>?> = MutableStateFlow(null)
    val checkHistoryResponse= _checkHistoryResponse.asStateFlow()

    private val _removeFavoriteResponse:MutableStateFlow<NetworkState<Boolean>?> = MutableStateFlow(null)
    val removeFavoriteResponse=_removeFavoriteResponse.asStateFlow()

    private val _removeHistoryResponse:MutableStateFlow<NetworkState<Boolean>?> = MutableStateFlow(null)
    val removeHistoryResponse=_removeHistoryResponse.asStateFlow()

    private val _getFavoriteResponse:MutableStateFlow<NetworkState<List<FavoriteFilm>>?> = MutableStateFlow(null)
    val getFavoriteResponse=_getFavoriteResponse.asStateFlow()

    private val _getHistoryResponse:MutableStateFlow<NetworkState<List<FavoriteFilm>>?> = MutableStateFlow(null)
    val getHistoryResponse= _getHistoryResponse.asStateFlow()

    private val _favoriteFilms = MutableStateFlow<List<FavoriteFilm>>(emptyList())
    val favoriteFilms= _favoriteFilms.asStateFlow()

    var errorMessage: String by mutableStateOf("")

    var movieId = mutableIntStateOf(0 )

    private val _profileInfo:MutableStateFlow<NetworkState<ProfileItemDTO>?> = MutableStateFlow(null)
    val profileInfo=_profileInfo.asStateFlow()

    private val _filmDetails = MutableStateFlow<DetailsResponse?>(null)
    val filmDetails = _filmDetails.asStateFlow()

    private val _addFavoriteFilms=MutableStateFlow<NetworkState<Boolean>?>(null)
    val addFavoriteFilms = _addFavoriteFilms.asStateFlow()

    private val _addHistory=MutableStateFlow<NetworkState<Boolean>?>(null)
    val addHistory = _addHistory.asStateFlow()

    private val _updatePasswordResponse: MutableStateFlow<NetworkState<Boolean>?> = MutableStateFlow(null)
    val updatePasswordResponse = _updatePasswordResponse.asStateFlow()

    private val _updateUserProfileResponse: MutableStateFlow<NetworkState<Boolean>?> = MutableStateFlow(null)
    val updateUserProfileResponse = _updateUserProfileResponse.asStateFlow()

    private val _profileImageUploadState:MutableStateFlow<NetworkState<String>?> = MutableStateFlow(null)
    val profileImageUploadState= _profileImageUploadState.asStateFlow()

    private val _profileImageUrl: MutableStateFlow<String?> = MutableStateFlow(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl

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


    fun addHistory(film: FavoriteFilm) {
        viewModelScope.launch(Dispatchers.IO) {
            mainPageRepository.addHistory(film).collectLatest {state->
                _addHistory.value=state

            }
        }
    }

    fun getFavoriteFilms(){
        viewModelScope.launch(Dispatchers.IO) {
            _getFavoriteResponse.value=NetworkState.Loading()
            mainPageRepository.getFavoriteFilms(movieId.intValue).collectLatest {state->
                _getFavoriteResponse.value=state
            }
        }

    }



    fun getHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            _getHistoryResponse.value=NetworkState.Loading()
            mainPageRepository.getHistory(movieId.intValue).collectLatest {state->
                _getHistoryResponse.value=state
            }
        }

    }

    fun addFavoriteLocal(film: FavoriteFilm) {
        viewModelScope.launch(Dispatchers.IO) {
            mainPageRepository.addFavoriteLocal(film)
        }
    }

    fun removeFavorite(removeMovieId:Int?=null) {
        viewModelScope.launch(Dispatchers.IO) {
         mainPageRepository.removeFavoriteFilm(removeMovieId?:movieId.intValue).collectLatest {state->
             _removeFavoriteResponse.value=state
         }
        }
    }


    fun removeHistory(removeMovieId:Int?=null) {
        viewModelScope.launch(Dispatchers.IO) {
            mainPageRepository.removeHistory(removeMovieId?:movieId.intValue).collectLatest {state->
                _removeHistoryResponse.value=state
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

    fun checkHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            mainPageRepository.checkHistoryFilm(movieId.intValue).collectLatest {state->
                _checkHistoryResponse.value=state
            }
        }

    }



    fun updateUserProfile(context: Context, name: String, email: String, newPassword:String, newConfirmPassword:String) {
        _updateUserProfileResponse.value = NetworkState.Loading()

        if (newPassword!= newConfirmPassword) {
            _updateUserProfileResponse.value =
                NetworkState.Error(context.getString(R.string.error_message))
            return
        }


        viewModelScope.launch(Dispatchers.IO) {
            mainPageRepository.updateUserProfile(name, email,newPassword,newConfirmPassword).collectLatest { state ->
                _updateUserProfileResponse.value = state
            }
        }
    }

    fun uploadProfileImage(uri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            mainPageRepository.uploadProfileImage(uri).collectLatest {state->
                _profileImageUploadState.value=state
                if (state is NetworkState.Success) {
                    _profileImageUrl.value = state.data
                }
            }
        }

    }

    fun resetFavorite() {
        _checkFavoriteResponse.value = null
    }

    fun resetHistory() {
        _checkHistoryResponse.value = null
    }


    fun resetRemoveFavoriteState() {
        _removeFavoriteResponse.value = null
    }


    fun resetRemoveHistoryState() {
        _removeHistoryResponse.value = null
    }

    fun resetAddFavoriteState() {
        _addFavoriteFilms.value = null
    }

    fun resetAddHistoryState() {
        _addHistory.value = null
    }

    fun resetEditProfile() {
        _updateUserProfileResponse.value = null
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
                    if (state is NetworkState.Success) {
                        _profileImageUrl.value = state.data.profileImage
                    }
                }
            }


        }
    }

    fun getFavoriteLocal(){
        viewModelScope.launch(Dispatchers.IO) {
      auth.currentUser?.uid?.let {uid->
          mainPageRepository.getFavoriteLocal(uid).collectLatest {films->
              _favoriteFilms.value=films

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

    fun getReviews(){

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val reviewItems= detailPageRepository.getReviews(movieId.intValue)
                _reviewListResponse.value= reviewItems.body()
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


    val upcomingListPager = Pager(
        config = PagingConfig(pageSize = 20),
        initialKey = 1,
        pagingSourceFactory = {
            UpcomingPagingSource(mainPageRepository)
        }
    ).flow.cachedIn(viewModelScope)

}