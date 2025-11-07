package com.example.bookreaderapp.screens.details

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bookreaderapp.model.Item
import com.example.bookreaderapp.utils.DataOrException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

@Composable
fun BookReaderDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: BookDetailsViewModel = hiltViewModel()
) {

//    val bookInfo = produceState<DataOrException<Item>>(initialValue = DataOrException(loading = false), producer = )

    val bookInfoState = viewModel.bookInfo
    
    LaunchedEffect(key1 = bookId){
        viewModel.getBookInfo(bookId)
    }

    when {
        bookInfoState.loading -> {
            CircularProgressIndicator()
        }
        bookInfoState.data != null -> {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {

                Text(text = "Book ${bookInfoState.data?.volumeInfo?.title}")
            }
        }
        else -> {
            Text(text = "Error loading data")
        }
    }

}

