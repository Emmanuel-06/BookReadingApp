package com.example.bookreaderapp.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.bookreaderapp.R

@Composable
fun BookReaderSearchScreen() {

    Box(
        modifier = Modifier.background(color = colorResource(id = R.color.primary_color))
    ){
        Text("Add item")

    }

}