package com.example.bookreaderapp.utils

sealed class FirebaseResponse<T>(val data: T? = null, val message: String? = null){

    class Success<T>(data: T): FirebaseResponse<T>(data)
    class Loading<T>: FirebaseResponse<T>()
    class Error<T>(message: String?, data: T? = null): FirebaseResponse<T>(data, message)
}