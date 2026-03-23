package com.example.recipecomposeapp

import android.content.Intent
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
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.data.model.toUiModel
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.ui.theme.navigation.AppNavigation
import com.example.recipecomposeapp.ui.theme.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.theme.navigation.Screen

@Composable
fun RecipesApp(deepLinkIntent: Intent?) {
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
                        deepLinkIntent = deepLinkIntent,
                        getRecipeById = { recipeId ->
                            RecipesRepositoryStub.getRecipeById(recipeId)?.toUiModel()
                        }
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
            RecipesApp(null)
        }
    }
}