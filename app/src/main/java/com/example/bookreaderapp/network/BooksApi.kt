package com.example.bookreaderapp.network

import com.example.bookreaderapp.model.Book
import com.example.bookreaderapp.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface  BooksApi{
    //https://www.googleapis.com/books/v1/volumes?q=kotlin
   @GET("books/v1/volumes")
   suspend fun getAllBooks(
        @Query("q") query: String
   ):Book

   @GET("books/v1/volumes/{bookId}")
   suspend fun getBookInfo(
       @Path("bookId") bookId: String
   ):Item
}