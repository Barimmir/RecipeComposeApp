package com.example.recipecomposeapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.ui.theme.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.theme.navigation.ScreenId

@Composable
fun RecipesApp() {
    var currentScreen by remember {
        mutableStateOf(ScreenId.CATEGORIES)
    }

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
                ScreenId.CATEGORIES -> CategoriesScreen()
                ScreenId.FAVORITES -> FavoritesScreen()
            }
        }
    }
}

@Composable
fun FavoritesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Экран Избранного"
        )
    }
}

@Composable
fun CategoriesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Экран Категорий"
        )
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