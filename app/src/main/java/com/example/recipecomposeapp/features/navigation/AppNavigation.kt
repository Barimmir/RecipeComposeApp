package com.example.recipecomposeapp.features.navigation

import android.app.Application
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipecomposeapp.app.di.AppContainer
import com.example.recipecomposeapp.app.di.RecipeDetailsViewModelFactory
import com.example.recipecomposeapp.app.di.RecipesViewModelFactory
import com.example.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.example.recipecomposeapp.features.core.utils.Constants
import com.example.recipecomposeapp.features.core.utils.shareRecipe
import com.example.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.example.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.example.recipecomposeapp.features.recipes.ui.RecipesScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    deepLinkIntent: Intent? = null,
    appContainer: AppContainer
) {
    val context = LocalContext.current
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
            CategoriesScreen(
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
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            val categoryTitle = backStackEntry.arguments?.getString("categoryTitle") ?: ""
            val categoryImageUrl = backStackEntry.arguments?.getString("categoryImageUrl") ?: ""
            
            android.util.Log.d("AppNavigation", "Received categoryId: $categoryId, title: $categoryTitle")

            val savedStateHandle = remember(backStackEntry) {
                SavedStateHandle(
                    mapOf(
                        "categoryId" to categoryId,
                        "categoryTitle" to categoryTitle,
                        "categoryImageUrl" to categoryImageUrl
                    )
                )
            }
            val recipesViewModel = remember {
                RecipesViewModelFactory(
                    savedStateHandle = savedStateHandle,
                    repository = appContainer.recipesRepository
                ).create()
            }
            RecipesScreen(
                viewModel = recipesViewModel,
                onRecipeClick = { recipeId, _ ->
                    navController.navigate(Screen.RecipeDetails.Base.createRoute(recipeId))
                }
            )
        }
        composable(
            route = Screen.RecipeDetails.Base.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val savedStateHandle = backStackEntry.savedStateHandle
            val recipeDetailsViewModel = remember {
                RecipeDetailsViewModelFactory(
                    application = context.applicationContext as Application,
                    savedStateHandle = savedStateHandle,
                    repository = appContainer.recipesRepository
                ).create()
            }
            RecipeDetailsScreen(
                viewModel = recipeDetailsViewModel,
                shareRecipe = { context, id, title ->
                    shareRecipe(context, id, title)
                }
            )
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                onRecipeClick = { recipeId, _ ->
                    navController.navigate(Screen.RecipeDetails.Base.createRoute(recipeId))
                },
                modifier = Modifier
            )
        }
    }
}
