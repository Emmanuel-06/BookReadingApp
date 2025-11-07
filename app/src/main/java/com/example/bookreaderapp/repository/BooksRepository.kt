package com.example.bookreaderapp.repository

import com.example.bookreaderapp.model.Item
import com.example.bookreaderapp.network.BooksApi
import com.example.bookreaderapp.utils.DataOrException
import javax.inject.Inject


class BooksRepository @Inject constructor(
    private val booksApi: BooksApi
) {
    suspend fun getAllBooks(booksQuery: String): DataOrException<List<Item>> {

        val allBooksResponse = try {
            booksApi.getAllBooks(booksQuery).items
        } catch (e: Exception){
            return DataOrException(exception = e)
        }

        return DataOrException(data = allBooksResponse)
    }


    suspend fun getBookInfo(bookInfoQuery: String): DataOrException<Item>{
        val bookInfoResponse = try {
            booksApi.getBookInfo(bookInfoQuery)
        } catch (e: Exception){
            return DataOrException(exception = e)
        }

        return DataOrException(data = bookInfoResponse)
    }
}







//    val response = DataOrException<List<Item>>()
//    response.loading = true
//
//    try {
//        val books = booksApi.getAllBooks(booksQuery).items ?: emptyList()
//        response.data = books
//            DataOrException(
//                data = books,
//                loading = false,
//                exception = null
//            )
//    } catch (e: Exception) {
//        response.exception = e
//            DataOrException(
//                data = emptyList(),
//                loading = false,
//                exception = e
//            )
//    }
//}


//
//suspend fun getBookInfo(bookId: String): DataOrException<Item> {
//    return try {
//        val bookInfo = booksApi.getBookInfo(bookId)
//        DataOrException(
//            data = bookInfo,
//            loading = false,
//            exception = null
//        )
//    } catch (e: Exception) {
//        DataOrException(
//            data = null,
//            loading = false,
//            exception = e
//        )
//    }
//}