package com.example.bookreaderapp.screens.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookreaderapp.model.Item
import com.example.bookreaderapp.repository.BooksRepository
import com.example.bookreaderapp.utils.DataOrException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(private val bookInfoRepository: BooksRepository) :
    ViewModel() {

    var bookInfo: DataOrException<Item> by mutableStateOf(
        DataOrException<Item>()
    )

    fun getBookInfo(bookId: String) {
        viewModelScope.launch {
            try {
                bookInfo = DataOrException(loading = true)
                val response = bookInfoRepository.getBookInfo(bookId)
                bookInfo = DataOrException(data = response.data, loading = false)
            } catch (e: Exception) {
                bookInfo = DataOrException(exception = e, loading = false)
            }
        }
    }
}
