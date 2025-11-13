package com.example.bookreaderapp.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreaderapp.model.MBook
import com.example.bookreaderapp.repository.FirebaseRepository
import com.example.bookreaderapp.utils.FirebaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): ViewModel() {

    var savedBooksList: MutableState<FirebaseResponse<List<MBook>>>
    = mutableStateOf(
        FirebaseResponse.Success(emptyList())
    )
    init {
        loadSavedBooks()
    }
    private fun loadSavedBooks() {
        viewModelScope.launch {
            try {
                savedBooksList.value = FirebaseResponse.Loading()
                savedBooksList.value = firebaseRepository.getAllBooks()
            } catch (e: Exception) {
                savedBooksList.value = FirebaseResponse.Error(e.message.toString())
            }
        }
    }
}