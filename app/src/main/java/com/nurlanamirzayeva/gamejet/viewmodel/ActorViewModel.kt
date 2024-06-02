package com.nurlanamirzayeva.gamejet.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurlanamirzayeva.gamejet.model.ActorDetailResponse
import com.nurlanamirzayeva.gamejet.model.ActorImageResponse
import com.nurlanamirzayeva.gamejet.model.ActorMoviesResponse
import com.nurlanamirzayeva.gamejet.network.repositories.ActorPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(private val actorRepository: ActorPageRepository) : ViewModel() {

    private val _actorDetailResponse: MutableStateFlow<ActorDetailResponse?> =
        MutableStateFlow(null)
    val actorDetailResponse = _actorDetailResponse.asStateFlow()

    private val _actorImageResponse: MutableStateFlow<ActorImageResponse?> = MutableStateFlow(null)
    val actorImageResponse = _actorImageResponse.asStateFlow()

    private val _actorMovieResponse: MutableStateFlow<ActorMoviesResponse?> = MutableStateFlow(null)
    val actorMoviesResponse = _actorMovieResponse.asStateFlow()

    var personId = mutableIntStateOf(0)


    fun getActorDetails() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = actorRepository.getActorDetails(personId.intValue)
            if (response.isSuccessful) {
                _actorDetailResponse.value = response.body()

            }
        }


    }

    fun getActorImages() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = actorRepository.getActorImages(personId.intValue)
            if (response.isSuccessful) {
                _actorImageResponse.value = response.body()

            }
        }


    }

    fun getActorMovies() {
        viewModelScope.launch(Dispatchers.IO) {

            val response = actorRepository.getActorMovies(personId.intValue)
            if (response.isSuccessful) {
                _actorMovieResponse.value = response.body()

            }
        }


    }

}