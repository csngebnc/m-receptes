package hu.bme.aut.android.receptes.ui.main

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.android.receptes.ui.details.RecipeDetails
import hu.bme.aut.android.receptes.ui.list.RecipeList
import hu.bme.aut.android.receptes.ui.login.Login


@Composable
fun RecipesMainScreen() {
    var username by remember { mutableStateOf("") }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.Login.route) {
        composable(NavScreen.Login.route) {
            Login(
                onLogin = {
                    username = it
                    navController.navigate(NavScreen.List.route)
                },
            )
        }
        composable(NavScreen.List.route) {
            RecipeList(
                username = username,
                viewModel = hiltViewModel(),
                selectRecipe = {
                    navController.navigate("${NavScreen.RecipeDetails.route}/$it")
                },
                addRecipe = {
                    navController.navigate("${NavScreen.RecipeDetails.route}/-1")
                }
            )
        }
        composable(NavScreen.RecipeDetails.routeWithArgument) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString(NavScreen.RecipeDetails.argument0)
                ?: null

            var id: Long? = null
            if (recipeId != null && recipeId.toLong() > 0) {
                id = recipeId.toLong()
            }

            RecipeDetails(
                username = username,
                recipeId = id,
                onBack = {
                    navController.popBackStack()
                },
                viewModel = hiltViewModel()
            )
        }
    }
}

sealed class NavScreen(val route: String) {

    object Login : NavScreen("Login")
    object List : NavScreen("List")

    object RecipeDetails : NavScreen("RecipeDetails") {

        const val routeWithArgument: String = "RecipeDetails/{recipeId}"

        const val argument0: String = "recipeId"
    }
}