package com.example.recipecomposeapp.features.navigation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipecomposeapp.features.categories.ui.CategoriesScreen
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesViewModel
import com.example.recipecomposeapp.features.favorites.ui.FavoritesScreen
import com.example.recipecomposeapp.features.recipes.ui.RecipesScreen
import com.example.recipecomposeapp.features.details.ui.RecipeDetailsScreen
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.features.core.utils.Constants
import com.example.recipecomposeapp.data.model.FavoriteDataStoreManager
import com.example.recipecomposeapp.features.core.utils.shareRecipe
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    deepLinkIntent: Intent? = null,
    getRecipeById: (Int) -> RecipesUiModel?,
    favoriteDataStoreManager: FavoriteDataStoreManager
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
            CategoriesScreen(
                modifier = Modifier,
                viewModel = CategoriesViewModel(),
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

            val savedStateHandle = SavedStateHandle(
                mapOf(
                    "categoryId" to categoryId,
                    "categoryTitle" to categoryTitle,
                    "categoryImageUrl" to categoryImageUrl
                )
            )
            RecipesScreen(
                viewModel = RecipesViewModel(savedStateHandle),
                onRecipeClick = { recipeId, recipe ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        KEY_RECIPE_OBJECT,
                        recipe
                    )
                    navController.navigate(Screen.RecipeDetails.Base.createRoute(recipeId))
                }
            )
        }
        composable(
            route = Screen.RecipeDetails.Base.route,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            val recipe = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<RecipesUiModel>(KEY_RECIPE_OBJECT)
                ?: return@composable Text("Recipe not found")
            RecipeDetailsScreen(
                recipeId = recipeId,
                recipe = recipe,
                shareRecipe = { context, id, title ->
                    shareRecipe(context, id, title)
                },
                favoriteDataStoreManager = favoriteDataStoreManager
            )
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen(
                recipesRepository = RecipesRepositoryStub,
                favoriteDataStoreManager = favoriteDataStoreManager,
                onRecipeClick = { recipeId, recipe ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        KEY_RECIPE_OBJECT,
                        recipe
                    )
                    navController.navigate(Screen.RecipeDetails.Base.createRoute(recipeId))
                },
                modifier = Modifier
            )
        }
    }
}
