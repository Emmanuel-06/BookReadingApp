package com.example.bookreaderapp.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bookreaderapp.R
import com.example.bookreaderapp.components.ReadingBookListCard
import com.example.bookreaderapp.components.ReadingListCard
import com.example.bookreaderapp.components.SectionTitle
import com.example.bookreaderapp.model.Item
import com.example.bookreaderapp.model.MBook
import com.example.bookreaderapp.navigation.BookReaderScreens
import com.example.bookreaderapp.screens.search.BookSearchViewModel
import com.example.bookreaderapp.ui.theme.InterFont
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReaderHomeScreen(
    navController: NavController = NavController(LocalContext.current),
    viewModel: BookSearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    var search = remember {
        mutableStateOf(false)
    }
    var query = remember {
        mutableStateOf("")
    }
    var active = remember {
        mutableStateOf(true)
    }

    val access = FirebaseAuth.getInstance().currentUser?.email

    //To do: Mbook data class
    val currentUserName = if (!access.isNullOrEmpty()) {
        access.split("@")[0]
    } else {
        "n/a"
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val listOfBooks = listOf<MBook>(
        MBook(id = "1223", title = "Deep Work", authors = "Author 1", notes = null),
        MBook(id = "1223", title = "Make Time", authors = "Author 2", notes = null),
        MBook(id = "1223", title = "The Purpose Driven Life", authors = "Author 3", notes = null),
        MBook(id = "1223", title = "Psychology of Money", authors = "Author 4", notes = null),
        MBook(id = "1223", title = "Richest Man in Babylon", authors = "Author 5", notes = null),
        MBook(id = "1223", title = "Rich Dad, Poor Dad", authors = "Author 6", notes = null),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Column {
                            Text(
                                text = "Hello, $currentUserName",
                                fontSize = 18.sp,
                                fontFamily = InterFont,
                                color = colorResource(id = R.color.black),
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.alpha(0.8f)
                            )
                            Text(
                                text = "What are you reading today?",
                                fontSize = 18.sp,
                                fontFamily = InterFont,
                                color = colorResource(id = R.color.black),
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                            )
                        }
                    },
                    actions = {
                        Image(
                            painter = painterResource(id = R.drawable.profile_photo),
                            contentDescription = "profile image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                    modifier = Modifier.padding(top = 20.dp)
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        search.value = !search.value
                    },
                    containerColor = colorResource(id = R.color.primary_color),
                    modifier = modifier
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.material_search),
                        contentDescription = "search",
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier

                    )
                }
            },

            ) { it ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 16.dp, vertical = 30.dp)
            ) {
                ReadingBookListCard()
                Spacer(modifier = Modifier.height(8.dp))
                SectionTitle(text = "My Reading List")
                ReadingListArea(listOfBooks = listOfBooks, onCardClicked = {})
            }
        }

        Box( contentAlignment = Alignment.Center){
            if (search.value) {
                SearchBar(
                    query = query.value,
                    onQueryChange = { query.value = it },
                    onSearch = {
                        viewModel.getAllBooks(it)
                        query.value = ""
                        keyboardController?.hide()
                    },
                    active = active.value,
                    onActiveChange = { active.value = it },
                    placeholder = {
                        Text(
                            text = "Search for a book....",
                            fontSize = 16.sp,
                            fontFamily = InterFont,
                            color = colorResource(id = R.color.black).copy(0.7f),
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    leadingIcon = {
                        IconButton(onClick = {
                            search.value = !search.value
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "arrow back",
                                modifier = Modifier.size(46.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .background(color = colorResource(id = R.color.white))
                ) {

                    val state = viewModel.listOfBooks.value

                    when {
                        state.loading -> {
                            CircularProgressIndicator()
                        }
                        state.data?.isEmpty() == true -> {
                            Text("No Books available")
                        }
                        else -> {
                            LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ){
                                items(state.data ?: emptyList()){ book ->
                                    SearchResultItem(book = book, viewModel = viewModel, navController = navController)

                                }
                            }
                        }
                    }
                }
            }

        }

    }
}


@Composable
fun SearchResultItem(
    book: Item,
    navController: NavController,
    viewModel: BookSearchViewModel
) {
    val imageUrl: String? = book.volumeInfo.imageLinks?.smallThumbnail ?.replace("http://", "https://")

    Log.d("IMMG", "SearchResultItem: $imageUrl")

    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp)
            .clickable { navController.navigate(BookReaderScreens.DETAILS_SCREEN.name + "/${book.id}") }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top,

        ) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "book cover",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(60.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
            ) {
                Text(
                    text = book.volumeInfo.title.orEmpty(),
                    fontSize = 18.sp,
                    fontFamily = InterFont,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.01).em
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 14.sp,
                                fontFamily = InterFont,
                                color = colorResource(id = R.color.black),
                                fontWeight = FontWeight.Medium,
                            )
                        ) {
                            append("by ")
                        }
                        Spacer(modifier = Modifier.width(8.dp))

                        withStyle(
                            SpanStyle(
                                fontSize = 14.sp,
                                fontFamily = InterFont,
                                color = colorResource(id = R.color.black),
                                fontWeight = FontWeight.Medium,
                            )
                        ) {
                            append(book.volumeInfo?.authors?.toString() ?: "Unknown author")
                        }
                    }
                )

                Text(
                    text = book.volumeInfo.description ?: "No Description Found",
                    fontSize = 14.sp,
                    fontFamily = InterFont,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }

        }

    }

}

@Composable
fun ReadingListArea(
    listOfBooks: List<MBook>,
    onCardClicked: (String) -> Unit
) {
    var scrollState = rememberScrollState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.horizontalScroll(scrollState)
    ) {

        for (book in listOfBooks) {
            ReadingListCard(book = book, modifier = Modifier) {

            }
        }
    }
}

