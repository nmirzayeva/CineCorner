package com.nurlanamirzayeva.gamejet.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nurlanamirzayeva.gamejet.R
import com.nurlanamirzayeva.gamejet.network.repositories.SignUpRepository
import com.nurlanamirzayeva.gamejet.utils.CONFIRM_PASSWORD
import com.nurlanamirzayeva.gamejet.utils.COUNTRY_CODE
import com.nurlanamirzayeva.gamejet.utils.EMAIL
import com.nurlanamirzayeva.gamejet.utils.NetworkState
import com.nurlanamirzayeva.gamejet.utils.PASSWORD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import java.util.Locale.IsoCountryCode
import javax.inject.Inject
@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: SignUpRepository):ViewModel() {

    private val _signUpSuccess = MutableStateFlow<NetworkState<Boolean>?>(null)
    val signUpSuccess = _signUpSuccess.asStateFlow()


    private val _errorMessage=MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun signUp(context: Context, userMap:HashMap<String,String>) {

        _signUpSuccess.value = NetworkState.Loading()

        if (userMap[PASSWORD] != userMap[CONFIRM_PASSWORD]) {
            _signUpSuccess.value = NetworkState.Error(context.getString(R.string.default_error_message))
            return
        }


        viewModelScope.launch(Dispatchers.Main) {
            repository.signUp(userMap).collectLatest {
               _signUpSuccess.value = it
            }

        }


    }

    fun reset(){
       _signUpSuccess.value = null
    }
}