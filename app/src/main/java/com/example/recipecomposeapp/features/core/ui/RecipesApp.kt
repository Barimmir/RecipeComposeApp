package com.example.recipecomposeapp.features.core.ui

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
import com.example.recipecomposeapp.features.navigation.AppNavigation
import com.example.recipecomposeapp.features.navigation.BottomNavigation
import com.example.recipecomposeapp.features.navigation.Screen
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme

@Composable
fun RecipesApp(
    deepLinkIntent: Intent?
) {
    val navController = rememberNavController()
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    val favoriteCount by favoritesViewModel.favoriteCount.collectAsState(initial = 0)

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
                        },
                        favoriteCount = favoriteCount
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
                        deepLinkIntent = deepLinkIntent
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
            RecipesApp(
                deepLinkIntent = null
            )
        }
    }
}
