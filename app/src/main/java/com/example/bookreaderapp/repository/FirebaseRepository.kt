package com.example.bookreaderapp.repository

import com.example.bookreaderapp.model.MBook
import com.example.bookreaderapp.utils.FirebaseResponse
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val queryBook: Query
) {

    suspend fun getAllBooks(): FirebaseResponse<List<MBook>> {

        val response = try {
            queryBook.get().await().documents.mapNotNull {
                it.toObject(MBook::class.java)
            }
        } catch (e: Exception) {
            return FirebaseResponse.Error(e.message)
        }

        return FirebaseResponse.Success(response)
    }
}
