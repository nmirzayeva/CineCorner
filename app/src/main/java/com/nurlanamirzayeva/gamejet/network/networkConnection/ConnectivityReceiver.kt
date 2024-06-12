package com.nurlanamirzayeva.gamejet.network.networkConnection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

class ConnectivityReceiver(
    private val context: Context,
    private val onNetworkChange: (Boolean) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        onNetworkChange(isNetworkAvailable(context))


    }

    private fun registerReceiver() {
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        context.registerReceiver(this, filter)
    }

    fun unregisterReceiver() {
        context.unregisterReceiver(this)
    }

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

        }

    }

}

@Composable
fun NetworkStatusMonitor(onNetworkChange: (Boolean) -> Unit) {
    val context = LocalContext.current
    var networkMonitor: ConnectivityReceiver? by remember { mutableStateOf(null) }

    DisposableEffect(context) {
        networkMonitor = ConnectivityReceiver(context) { isConnected ->
            onNetworkChange(isConnected)

        }
        onDispose {
            networkMonitor?.unregisterReceiver()
        }

    }


}