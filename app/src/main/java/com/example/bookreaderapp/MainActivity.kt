package com.example.bookreaderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bookreaderapp.navigation.BookReaderNav
import com.example.bookreaderapp.ui.theme.BookReaderAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            BookReaderAppTheme {
               BookReaderNav()
            }

        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}