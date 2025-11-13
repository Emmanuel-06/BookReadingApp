package com.example.bookreaderapp.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookreaderapp.screens.createaccount.BookReaderCreateAccountScreen
import com.example.bookreaderapp.screens.details.BookReaderDetailsScreen
import com.example.bookreaderapp.screens.home.BookReaderHomeScreen
import com.example.bookreaderapp.screens.home.HomeScreenViewModel
import com.example.bookreaderapp.screens.login.BookReaderLoginScreen
import com.example.bookreaderapp.screens.stats.BookReaderStatsScreen
import com.example.bookreaderapp.screens.update.BookReaderUpdateScreen
import com.example.bookreaderapp.utils.AuthViewModel

@Composable
fun BookReaderNav() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val startDestination by authViewModel.startDestination.collectAsState()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(BookReaderScreens.CREATE_ACCOUNT_SCREEN.name) {
            BookReaderCreateAccountScreen(navController)
        }

        composable(BookReaderScreens.LOGIN_SCREEN.name) {
            BookReaderLoginScreen(navController)
        }

        composable(BookReaderScreens.HOME_SCREEN.name) {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            BookReaderHomeScreen(navController = navController, homeScreenViewModel = viewModel)
        }

        composable(
            route = BookReaderScreens.DETAILS_SCREEN.name + "/{bookId}",
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookReaderDetailsScreen(navController = navController, bookId = it.toString())
            }
        }

        composable(BookReaderScreens.STATS_SCREEN.name) {
            BookReaderStatsScreen()
        }

        composable(
            route = BookReaderScreens.UPDATE_SCREEN.name + "/{bookUpdate}",
            arguments = listOf(
                navArgument(name = "bookUpdate") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookUpdate")?.let {
                BookReaderUpdateScreen(updateBook = it)
            }
        }
    }

}
