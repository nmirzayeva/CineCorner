package com.nurlanamirzayeva.gamejet.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel:ViewModel() {
    private var _name= MutableStateFlow("")
    var name = _name.asStateFlow()

    private var _email= MutableStateFlow("")
    var email = _email.asStateFlow()


}