package com.nurlanamirzayeva.gamejet.network.networkConnection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.log

class NetworkConnection(private val context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isConnected = MutableStateFlow<Boolean?>(null)
    val isConnected = _isConnected.asStateFlow()

    init {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                Log.d("TAG", "onAvailable: ")
                _isConnected.value = true
            }

            override fun onLost(network: android.net.Network) {
                _isConnected.value = false
            }
        })
    }
}
