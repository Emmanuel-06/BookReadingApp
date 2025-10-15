package com.example.bookreaderapp.repository

import com.example.bookreaderapp.model.Item
import com.example.bookreaderapp.network.BooksApi
import com.example.bookreaderapp.utils.DataOrException
import javax.inject.Inject


class BooksRepository @Inject constructor(
    private val booksApi: BooksApi
) {
    suspend fun getAllBooks(booksQuery: String): DataOrException<List<Item>> {
        return try {
            val books = booksApi.getAllBooks(booksQuery).items ?: emptyList()
            DataOrException(
                data = books,
                loading = false,
                exception = null
            )
        } catch (e: Exception) {
            DataOrException(
                data = emptyList(),
                loading = false,
                exception = e
            )
        }
    }


//    suspend fun getBookInfo(bookId: String): DataOrException<Item, Exception> {
//        val apiBookIdCallResponse = try {
//            booksApi.getBookInfo(bookId)
//        } catch (e: Exception) {
//            return DataOrException(exception = e)
//        }
//        return DataOrException(data = apiBookIdCallResponse)
//    }
}


