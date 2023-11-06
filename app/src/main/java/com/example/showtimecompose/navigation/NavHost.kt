package com.example.showtimecompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.showtimecompose.views.detail_screen.ShowDetailScreen
import com.example.showtimecompose.views.favouriteScreen.FavouritesScreen
import com.example.showtimecompose.views.home_screen.ShowListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            ShowListScreen(navController=navController)
        }
        composable(
            route = "${NavigationItem.Detail.route}?id={showId}",
            arguments = listOf(
                navArgument("showId") {
                    type = NavType.StringType
                    nullable = false
                },
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("showId")?.let { ShowDetailScreen(it) }
        }
        composable(NavigationItem.Favourites.route) {
            FavouritesScreen(navController=navController)
        }
    }
}
