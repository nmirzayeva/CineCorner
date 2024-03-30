package com.nurlanamirzayeva.gamejet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurlanamirzayeva.gamejet.network.repositories.SignInRepository
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: SignInRepository) : ViewModel() {

    private val _signInSuccess = MutableStateFlow<NetworkState<Boolean>?>(null)
    val signInSuccess = _signInSuccess.asStateFlow()


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.signIn(email, password).collectLatest {
                _signInSuccess.value = it
            }


        }


    }

}