package com.nurlanamirzayeva.gamejet.utils

sealed class NetworkState<T>(data:T?=null,errorMessage:String?=null){
    data class Success<T>(val data:T):NetworkState<T>(data)
    data class Error<T>(val errorMessage: String?):NetworkState<T>(null,errorMessage)
    class Loading<T>:NetworkState<T>()
}