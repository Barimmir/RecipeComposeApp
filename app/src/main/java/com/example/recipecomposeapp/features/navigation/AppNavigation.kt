package com.example.recipecomposeapp.features.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesViewModel
import com.example.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.example.recipecomposeapp.features.core.utils.Constants
import com.example.recipecomposeapp.features.core.utils.shareRecipe
import com.example.recipecomposeapp.features.details.presentation.model.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.example.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesViewModel
import com.example.recipecomposeapp.features.recipes.ui.RecipesScreen
import kotlinx.coroutines.delay

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    deepLinkIntent: Intent? = null
) {
    LaunchedEffect(deepLinkIntent) {
        deepLinkIntent?.data?.let { uri ->
            val recipeId: Int? = when (uri.scheme) {
                Constants.DEEP_LINK_SCHEME ->
                    if (uri.host == "recipe") uri.pathSegments[0].toIntOrNull() else null

                "https", "http" ->
                    if (uri.pathSegments[0] == "recipe") uri.pathSegments[1].toIntOrNull() else null

                else -> null
            }

            if (recipeId != null) {
                delay(100)
                navController.navigate(Screen.RecipeDetails.Base.createRoute(recipeId))
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Categories.route,
        modifier = modifier
    ) {
        composable(route = Screen.Categories.route) {
            val viewModel: CategoriesViewModel = hiltViewModel()
            CategoriesScreen(
                viewModel = viewModel,
                modifier = Modifier,
                onCategoryClick = { id, title, imageUrl ->
                    navController.navigate(Screen.Recipes.createRoute(id, title, imageUrl))
                }
            )
        }
        composable(
            route = Screen.Recipes.route,
            arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType },
                navArgument("categoryTitle") { type = NavType.StringType },
                navArgument("categoryImageUrl") { type = NavType.StringType })
        ) { _ ->
            val viewModel: RecipesViewModel = hiltViewModel()
            RecipesScreen(
                viewModel = viewModel,
                onRecipeClick = { recipeId, _ ->
                    navController.navigate(Screen.RecipeDetails.Base.createRoute(recipeId))
                }
            )
        }
        composable(
            route = Screen.RecipeDetails.Base.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { _ ->
            val viewModel: RecipeDetailsViewModel = hiltViewModel()
            RecipeDetailsScreen(
                viewModel = viewModel,
                shareRecipe = { context, id, title ->
                    shareRecipe(context, id, title)
                }
)         }
        composable(route = Screen.Favorites.route) {
            val viewModel: FavoritesViewModel = hiltViewModel()
            FavoritesScreen(
                viewModel = viewModel,
                onRecipeClick = { recipeId, _ ->
                    navController.navigate(Screen.RecipeDetails.Base.createRoute(recipeId))
                },
                modifier = Modifier
            )
        }
    }
}
