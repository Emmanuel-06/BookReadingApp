package com.example.bookreaderapp.screens.search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreaderapp.model.Item
import com.example.bookreaderapp.repository.BooksRepository
import com.example.bookreaderapp.utils.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(
    private val booksRepository: BooksRepository
): ViewModel() {

    var listOfBooks: MutableState<DataOrException<List<Item>>> = mutableStateOf(
        DataOrException(data = emptyList(), loading = true)
    )

    init {
        getAllBooks("finance")
    }

    fun getAllBooks(bookQuery: String) {
        viewModelScope.launch {
            if (bookQuery.isEmpty()) {
                return@launch
            }
            listOfBooks.value = DataOrException(data = emptyList(), loading = true)

            listOfBooks.value = booksRepository.getAllBooks(bookQuery)
            Log.d("DATA", "getAllBooks: ${listOfBooks.value.data.toString()}")

            if (listOfBooks.value.data.toString().isNotEmpty()) {
                listOfBooks.value.loading = false
            }

        }
    }
}