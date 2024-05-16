package com.nurlanamirzayeva.gamejet.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class MovieSharedPreferences(context: Context) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("shared_preferences", MODE_PRIVATE)

    private val editor = sharedPreferences.edit()

    private fun setString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    private fun getString(key: String, defValue: String? = null): String {
        return sharedPreferences.getString(key, defValue) ?: ""
    }

    var themeMode: String
        get() = getString("themeMode", ThemeMode.default().name)
        set(value) {
            setString("themeMode", value)
        }
}