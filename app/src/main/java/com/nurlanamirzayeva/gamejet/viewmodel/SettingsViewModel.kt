package com.nurlanamirzayeva.gamejet.viewmodel

import androidx.lifecycle.ViewModel
import com.nurlanamirzayeva.gamejet.utils.MovieSharedPreferences
import com.nurlanamirzayeva.gamejet.utils.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val sharedPreferences: MovieSharedPreferences) : ViewModel() {

    private val _currentThemeMode = MutableStateFlow<ThemeMode>(ThemeMode.valueOf(sharedPreferences.themeMode))
    val currentThemeMode
        get() = _currentThemeMode.asStateFlow()


    private fun getSavedThemeMode(): ThemeMode {
        val savedThemeModeString = sharedPreferences.themeMode
        return try {
            ThemeMode.valueOf(savedThemeModeString)
        } catch (e: IllegalArgumentException) {
            ThemeMode.default()
        }
    }

    fun refreshTheme(){
        _currentThemeMode.value=getSavedThemeMode()
    }

    fun setTheme(themeMode: ThemeMode){
        sharedPreferences.themeMode=themeMode.toString()
    }

}