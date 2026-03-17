package com.example.recipecomposeapp.ui.theme.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipecomposeapp.ui.theme.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.theme.categories.model.CategoriesViewModel
import com.example.recipecomposeapp.ui.theme.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.theme.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.theme.recipes.components.RecipeDetailsScreen
import com.example.recipecomposeapp.ui.theme.recipes.model.RecipeUiModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Categories.route,
        modifier = modifier
    ) {
        composable(route = Screen.Categories.route) {
            CategoriesScreen(
                modifier = Modifier,
                viewModel = CategoriesViewModel(),
                onCategoryClick = { category ->
                    navController.navigate(Screen.Recipes.createRoute(category.id))
                }
            )
        }

        composable(
            route = Screen.Recipes.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId") ?: 0
            RecipesScreen(
                categoryId = categoryId,
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
                ?.get<RecipeUiModel>(KEY_RECIPE_OBJECT)
                ?: return@composable Text("Recipe not found")
            RecipeDetailsScreen(
                recipeId = recipeId,
                recipe = recipe
            )
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen()
        }
    }
}
