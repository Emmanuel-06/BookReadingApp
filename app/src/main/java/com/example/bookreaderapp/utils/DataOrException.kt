package com.example.bookreaderapp.utils

class DataOrException<T>(
    var data: T,
    var loading: Boolean = false,
    var exception: Exception? = null
)