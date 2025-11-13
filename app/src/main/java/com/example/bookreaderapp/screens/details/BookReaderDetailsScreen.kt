package com.example.bookreaderapp.screens.details

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bookreaderapp.R
import com.example.bookreaderapp.model.Item
import com.example.bookreaderapp.model.MBook
import com.example.bookreaderapp.utils.DataOrException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

@Composable
fun BookReaderDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: BookDetailsViewModel = hiltViewModel()
) {
    val bookInfoState = viewModel.bookInfo

    LaunchedEffect(key1 = bookId) {
        viewModel.getBookInfo(bookId)
    }

    when {
        bookInfoState.loading -> {
            CircularProgressIndicator()
        }

        bookInfoState.data != null -> {
            BookDetails(bookInfo = bookInfoState, navController)
        }

        else -> {
            Text(text = "Error loading data")
        }
    }

}

@Composable
fun BookDetails(
    bookInfo: DataOrException<Item> = DataOrException(),
    navController: NavController) {

    val imageUrl: String? =
        bookInfo.data?.volumeInfo?.imageLinks?.smallThumbnail?.replace("http://", "https://")
    val scrollState = rememberScrollState()

    val cleanDescription =
        bookInfo.data?.volumeInfo?.description?.let {
            HtmlCompat.fromHtml(
                it,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString()
        }

    val bookData = bookInfo.data?.volumeInfo
    val userId = Firebase.auth.currentUser?.uid

    val book = MBook(
        title = bookData?.title,
        authors = bookData?.authors,
        publishedDate = bookData?.publishedDate,
        categories = bookData?.categories,
        description = bookData?.description,
        pageCount = bookData?.pageCount.toString(),
        photoUrl = bookData?.imageLinks?.thumbnail,
        userId = userId
    )

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(horizontal = 21.dp, vertical = 24.dp)
            .fillMaxHeight()
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(40.dp),
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = bookInfo.data?.volumeInfo?.title ?: "Unknown Title",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = -(0.01).em,
                    lineHeight = 1.2.em,
                    color = colorResource(id = R.color.black),
                    modifier = Modifier.width(160.dp)
                )
                Text(
                    text = ("By " + bookInfo.data?.volumeInfo?.authors),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.black),
                )
                Text(
                    text = ("Published " + bookInfo.data?.volumeInfo?.publishedDate),
                    fontSize = 16.sp,
                    color = colorResource(id = R.color.black),
                )
            }

            AsyncImage(
                model = imageUrl,
                contentDescription = "book image",
                modifier = Modifier
                    .height(180.dp)
                    .weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))


        Surface(
            color = colorResource(id = R.color.grey300),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(16.dp)

            ) {
                Text(
                    text = "Summary",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                if (cleanDescription != null) {
                    Text(
                        text = cleanDescription,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .verticalScroll(scrollState)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
        Button(
            onClick = {
                saveToFireBase(book, context, navController)
            },
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_color)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }

}

fun saveToFireBase(book: MBook, context: Context, navController: NavController) {
    val firebaseDb = FirebaseFirestore.getInstance()
    val dbCollection = firebaseDb.collection("books")

    dbCollection.add(book)
        .addOnSuccessListener { docRef ->
//                val docId = docRef.id
//                dbCollection.document(docId)
//                  .update(hashMapOf("id" to docId) as Map <String, Any>()
            Toast.makeText(context, "Book Successfully Added", Toast.LENGTH_LONG).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Adding Book Failed", Toast.LENGTH_LONG).show()
        }
    navController.popBackStack()
}

