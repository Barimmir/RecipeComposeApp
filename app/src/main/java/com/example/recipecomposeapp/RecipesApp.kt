package com.example.recipecomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.ui.theme.navigation.AppNavigation
import com.example.recipecomposeapp.ui.theme.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.theme.navigation.Screen

@Composable
fun RecipesApp() {
    val navController = rememberNavController()
    RecipeComposeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,

            ) {
            Scaffold(
                bottomBar = {
                    BottomNavigation(
                        onCategoriesClick = {
                            navController.navigate(Screen.Categories.route) {
                                popUpTo(Screen.Categories.route) { inclusive = true }
                            }
                        },
                        onFavoriteClick = {
                            navController.navigate(Screen.Favorites.route) {
                                popUpTo(Screen.Favorites.route) { inclusive = true }
                            }
                        },
                        onRecipesClick = {
                            navController.navigate(Screen.Recipes.route) {
                                popUpTo(Screen.Recipes.route) { inclusive = true }
                            }
                        }
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    AppNavigation(
                        navController = navController,
                        onRecipeClick = { recipeId -> }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesAppPreview() {
    RecipeComposeAppTheme {
        Surface {
            RecipesApp()
        }
    }
}