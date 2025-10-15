package com.example.bookreaderapp.utils

import androidx.lifecycle.ViewModel
import com.example.bookreaderapp.navigation.BookReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel: ViewModel() {

    private val _startDestination = MutableStateFlow(BookReaderScreens.LOGIN_SCREEN.name)
    val startDestination: StateFlow<String> = _startDestination

    init {
        val user = FirebaseAuth.getInstance().currentUser
//        _startDestination.value = if (user == null) {
//            BookReaderScreens.LOGIN_SCREEN.name
//        } else {
//            BookReaderScreens.HOME_SCREEN.name
//        }

        _startDestination.value = BookReaderScreens.LOGIN_SCREEN.name
    }
}
