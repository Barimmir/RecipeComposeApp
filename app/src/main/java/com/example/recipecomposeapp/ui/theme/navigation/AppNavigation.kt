package com.example.recipecomposeapp.ui.theme.navigation

import android.annotation.SuppressLint
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
import com.example.recipecomposeapp.ui.theme.recipes.model.RecipeViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onRecipeClick: (Int) -> Unit
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
                viewModel = RecipeViewModel(),
                onRecipeClick = onRecipeClick
            )
        }
        composable(route = Screen.Favorites.route) {
            FavoritesScreen()
        }
    }
}
