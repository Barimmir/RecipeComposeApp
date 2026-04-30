package com.example.recipecomposeapp.features.core.ui

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.data.model.toUiModel
import com.example.recipecomposeapp.features.navigation.AppNavigation
import com.example.recipecomposeapp.data.model.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.model.RecipeDto
import com.example.recipecomposeapp.features.core.network.api.RecipesApiService
import com.example.recipecomposeapp.features.navigation.BottomNavigation
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.navigation.Screen

@Composable
fun RecipesApp(
    deepLinkIntent: Intent?,
    apiService: RecipesApiService
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val favoriteManager = remember { FavoriteDataStoreManager(context) }
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
                        favoriteDataStoreManager = favoriteManager
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
                            kotlinx.coroutines.runBlocking {
                                RecipesRepositoryStub.getRecipe(recipeId)?.toUiModel()
                            }
                        },
                        favoriteDataStoreManager = favoriteManager,
                        apiService = apiService
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
        val mockApiService = object : RecipesApiService {
            override suspend fun getCategories() = RecipesRepositoryStub.getCategories()
            override suspend fun getRecipesByCategory(categoryId: Int) = 
                RecipesRepositoryStub.getRecipesByCategory(categoryId)
            override suspend fun getRecipe(recipeId: Int): RecipeDto {
                return RecipesRepositoryStub.getRecipe(recipeId) 
                    ?: throw IllegalArgumentException("Recipe not found")
            }
        }
        
        Surface {
            RecipesApp(
                deepLinkIntent = null,
                apiService = mockApiService
            )
        }
    }
}