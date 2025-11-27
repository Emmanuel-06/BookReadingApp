package com.example.bookreaderapp.screens.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.bookreaderapp.R
import com.example.bookreaderapp.model.MBook
import com.example.bookreaderapp.screens.home.HomeScreenViewModel
import com.example.bookreaderapp.ui.theme.InterFont
import com.example.bookreaderapp.utils.FirebaseResponse
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun BookReaderUpdateScreen(
    updateBook: String,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
) {

    val bookInfo by homeScreenViewModel.savedBooksList.collectAsState()

    LaunchedEffect(key1 = Unit) {
        homeScreenViewModel.loadSavedBooks(Firebase.auth.currentUser?.uid ?: return@LaunchedEffect)
    }

    when (bookInfo) {
        is FirebaseResponse.Success -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.padding(horizontal = 21.dp, vertical = 20.dp)
            ) {
                CardItem(book = bookInfo.data?.first{ it.googleBookId == updateBook }!!)

//                bookInfo.data?.first{ mBook -> mBook.googleBookId == updateBook }
//                    ?.let { CardItem(book = it) }
            }
//                ReadUpdateInfo(book = bookInfo)
            }

        is FirebaseResponse.Loading -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is FirebaseResponse.Error -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "An error occurred")
            }
        }
    }
}


@Composable
fun CardItem(
    book: MBook
) {

    Surface(
        color = colorResource(id = R.color.grey300),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {
            AsyncImage(
                model = book.photoUrl?.replace("http://", "https://"),
                contentDescription = "book Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(
                    height = 120.dp,
                    width = 90.dp
                )
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,

                    ) {
                    Text(
                        text = book.title.toString(),
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontFamily = InterFont,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                    book.authors?.forEach{ author ->

                        Text(
                            text = author,
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontFamily = InterFont,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Text(
                    text = "rating",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@Composable
fun Review() {

}
@Composable
fun ReadUpdateInfo(
    book: MBook

) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = { /*TODO*/ } ){
            Text(
                text = "Start Reading",
                fontSize = 14.sp,
                fontFamily = InterFont,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.primary_color)
            )
        }

        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = "Mark as Read",
                fontSize = 14.sp,
                fontFamily = InterFont,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.primary_color)
            )
        }
    }
}

@Composable
fun RatingSection() {

}