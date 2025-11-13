package com.example.bookreaderapp.navigation

import okhttp3.Route

enum class BookReaderScreens {
    LOGIN_SCREEN,
    CREATE_ACCOUNT_SCREEN,
    HOME_SCREEN,
    DETAILS_SCREEN,
    UPDATE_SCREEN,
    STATS_SCREEN;

    companion object {
        fun fromRoute(route: String?): BookReaderScreens = when (route?.substringBefore("/")) {
            LOGIN_SCREEN.name -> LOGIN_SCREEN
            CREATE_ACCOUNT_SCREEN.name -> CREATE_ACCOUNT_SCREEN
            HOME_SCREEN.name -> HOME_SCREEN
            DETAILS_SCREEN.name -> DETAILS_SCREEN
            UPDATE_SCREEN.name -> UPDATE_SCREEN
            STATS_SCREEN.name -> STATS_SCREEN
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}
