package com.abhinav12k.MixdUp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abhinav12k.MixdUp.presentation.drinkDetail.DrinkDetailScreen
import com.abhinav12k.MixdUp.presentation.drinkList.DrinkListScreen
import com.abhinav12k.MixdUp.presentation.favoriteScreen.FavoriteDrinksScreen
import com.abhinav12k.MixdUp.presentation.ui.theme.MixdUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MixdUpTheme {
                MixdUpMainScreen()
            }
        }
    }
}

@Composable
fun MixdUpMainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.DrinkListScreen.route) {

        composable(route = NavScreen.DrinkListScreen.route) {
            DrinkListScreen(
                viewModel = hiltViewModel(),
                favoriteDrinksViewModel = hiltViewModel(),
                onDrinkCardClicked = { drinkId ->
                    navController.navigate("${NavScreen.DrinkDetailScreen.route}/$drinkId")
                },
                onViewAllClicked = {
                    navController.navigate(NavScreen.FavoriteDrinksScreen.route)
                })
        }

        composable(
            route = NavScreen.DrinkDetailScreen.routeWithArgument,
            arguments = listOf(
                navArgument(NavScreen.DrinkDetailScreen.argument0) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val drinkId =
                navBackStackEntry.arguments?.getString(NavScreen.DrinkDetailScreen.argument0)
                    ?: return@composable
            DrinkDetailScreen(
                viewModel = hiltViewModel(),
                ingredientViewModel = hiltViewModel(),
                drinkId = drinkId,
                onBackPressed = { navController.navigateUp() })
        }

        composable(
            route = NavScreen.FavoriteDrinksScreen.route
        ) {
            FavoriteDrinksScreen(viewModel = hiltViewModel()) { drinkId ->
                navController.navigate("${NavScreen.DrinkDetailScreen.route}/$drinkId")
            }
        }
    }

}

sealed class NavScreen(val route: String) {
    object DrinkListScreen : NavScreen("DrinkListScreen")
    object DrinkDetailScreen : NavScreen("DrinkDetailScreen") {
        const val routeWithArgument: String = "DrinkDetailScreen/{drinkId}"
        const val argument0: String = "drinkId"
    }
    object FavoriteDrinksScreen : NavScreen("FavoriteDrinksScreen")
}