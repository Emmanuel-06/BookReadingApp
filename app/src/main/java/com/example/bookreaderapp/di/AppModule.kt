package com.example.bookreaderapp.di

import com.example.bookreaderapp.network.BooksApi
import com.example.bookreaderapp.repository.FirebaseRepository
import com.example.bookreaderapp.utils.Constants
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesFirebaseRepository() =
        FirebaseRepository(
            queryBook = Firebase.firestore
                .collection("books")
        )

    @Singleton
    @Provides
    fun providesBookApi():BooksApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }
}


//    @Singleton
//    @Provides
//    fun providesBookRepository(booksApi: BooksApi) = BooksRepository(booksApi)
