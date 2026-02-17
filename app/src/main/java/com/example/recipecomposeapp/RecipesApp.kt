package com.example.recipecomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.ui.theme.categories.CategoriesScreen
import com.example.recipecomposeapp.ui.theme.categories.model.CategoriesViewModel
import com.example.recipecomposeapp.ui.theme.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.theme.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.theme.navigation.ScreenId
import com.example.recipecomposeapp.ui.theme.recipes.RecipesScreen

@Composable
fun RecipesApp() {
    var currentScreen by remember {
        mutableStateOf(ScreenId.CATEGORIES)
    }
    RecipeComposeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onSurface,

        ) {
            Scaffold(
                bottomBar = {
                    BottomNavigation(
                        onCategoriesClick = {
                            currentScreen = ScreenId.CATEGORIES
                        },
                        onFavoriteClick = {
                            currentScreen = ScreenId.FAVORITES
                        },
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (currentScreen) {
                        ScreenId.CATEGORIES -> CategoriesScreen(viewModel = CategoriesViewModel())
                        ScreenId.FAVORITES -> FavoritesScreen()
                        ScreenId.RECIPES -> RecipesScreen()
                    }
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