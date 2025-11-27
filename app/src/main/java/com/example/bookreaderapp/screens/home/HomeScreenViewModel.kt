package com.example.bookreaderapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreaderapp.model.MBook
import com.example.bookreaderapp.repository.FirebaseRepository
import com.example.bookreaderapp.utils.FirebaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
): ViewModel() {

    private var _savedBooksList  = MutableStateFlow<FirebaseResponse<List<MBook>>>(FirebaseResponse.Loading())
    val savedBooksList = _savedBooksList.asStateFlow()

    fun loadSavedBooks(userId: String) {
        viewModelScope.launch {
            try {
                _savedBooksList.value = FirebaseResponse.Loading()

                when(val response = firebaseRepository.getAllBooks()){
                    is FirebaseResponse.Success -> {
                        _savedBooksList.value = FirebaseResponse.Success(response.data?.filter { it.userId == userId }!!)
                    }
                    else -> {}
                }
            } catch (e: Exception) {
                _savedBooksList.value = FirebaseResponse.Error(e.message.toString())
            }
        }
    }
}