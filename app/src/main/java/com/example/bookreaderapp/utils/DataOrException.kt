package com.example.bookreaderapp.utils

class DataOrException<T>(
    var data: T? = null,
    var loading: Boolean = false,
    var exception: Exception? = null
)