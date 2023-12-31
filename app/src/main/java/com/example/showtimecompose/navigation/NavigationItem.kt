package com.example.showtimecompose.navigation

enum class Screen {
    HOME,
    DETAIL,
    FAVOURITES
}
sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(Screen.HOME.name)
    data object Detail : NavigationItem(Screen.DETAIL.name)
    data object Favourites : NavigationItem(Screen.FAVOURITES.name)
}
