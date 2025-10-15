package com.example.bookreaderapp.di

import com.example.bookreaderapp.network.BooksApi
import com.example.bookreaderapp.repository.BooksRepository
import com.example.bookreaderapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesBookApi():BooksApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApi::class.java)
    }


//    @Singleton
//    @Provides
//    fun providesBookRepository(booksApi: BooksApi) = BooksRepository(booksApi)

}