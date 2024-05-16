package com.nurlanamirzayeva.gamejet.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.network.repositories.Repository
import com.nurlanamirzayeva.gamejet.utils.CONFIRM_PASSWORD
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.utils.PASSWORD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _signInSuccess = MutableStateFlow<NetworkState<Boolean>?>(null)
    val signInSuccess = _signInSuccess.asStateFlow()

    private val _signUpSuccess = MutableStateFlow<NetworkState<Boolean>?>(null)
    val signUpSuccess = _signUpSuccess.asStateFlow()

    private val _resetPasswordSuccess = MutableStateFlow<NetworkState<Boolean>?>(null)
    val resetPasswordSuccess = _resetPasswordSuccess.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _userName=MutableStateFlow("")
    val userName=_userName.asStateFlow()

    private val _userEmail=MutableStateFlow("")
    val userEmail =_userEmail.asStateFlow()

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    fun signIn(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _signInSuccess.value = NetworkState.Loading()
            repository.signIn(email, password).collectLatest {state->
                _signInSuccess.value = state
                if(state is NetworkState.Success){
                    val userId=auth.currentUser?.uid ?:""
                    fetchUserData(userId)

                }
            }


        }


    }

    private fun fetchUserData(userId:String){
        viewModelScope.launch(Dispatchers.IO) {
            val userData=repository.getUserData(userId)
            _userName.value=userData.first ?:""
            _userEmail.value=userData.second ?:""
        }
    }

    fun signUp(context: Context, userMap: HashMap<String, String>) {

        _signUpSuccess.value = NetworkState.Loading()

        if (userMap[PASSWORD] != userMap[CONFIRM_PASSWORD]) {
            _signUpSuccess.value =
                NetworkState.Error(context.getString(R.string.error_message))
            return
        }


        viewModelScope.launch(Dispatchers.IO) {
            repository.signUp(userMap).collectLatest {
                _signUpSuccess.value = it
            }

        }

    }

    fun resetPassword(email: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _resetPasswordSuccess.value = NetworkState.Loading()
            repository.resetPassword(email).collectLatest {

                _resetPasswordSuccess.value = it
            }


        }


    }


    fun errorMessage(email: String, password: String, confirmPassword: String?): String? {
        var message: String? = null
        if (email.isEmpty()) {

            message = "Please fill out e-mail field"
            return message

        }

        if (password.isEmpty()) {

            message = "Please fill out password field"
            return message
        }
        confirmPassword?.let {
            if (it.isEmpty()) {

                message = "Please confirm the password "
                return message
            }
        }


        if (!isEmailValid(email)) {
            message = "Incorrect email"
            return message
        }

        if (!isPasswordValid(password)) {
            message = "Incorrect password"
            return message

        }

        return message
    }


    fun reset() {
        _signInSuccess.value = null
    }

    fun resetPass() {

        _resetPasswordSuccess.value = null
    }


    fun isEmailValid(email: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0–9._-]+@[a-z]+\\.+[a-z]+")
        return emailPattern.matches(email)
    }


    fun isPasswordValid(password: String): Boolean {
        val passwordPattern =
            Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
        return passwordPattern.matches(password)

    }

    fun isConfirmPasswordValid(confirmPassword: String): Boolean {
        val passwordPattern =
            Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
        return passwordPattern.matches(confirmPassword)

    }

}

