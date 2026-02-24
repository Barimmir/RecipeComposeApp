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
import com.example.recipecomposeapp.ui.theme.categories.model.CategoryUiModel
import com.example.recipecomposeapp.ui.theme.favorites.FavoritesScreen
import com.example.recipecomposeapp.ui.theme.navigation.BottomNavigation
import com.example.recipecomposeapp.ui.theme.navigation.ScreenId
import com.example.recipecomposeapp.ui.theme.recipes.RecipesScreen
import com.example.recipecomposeapp.ui.theme.recipes.model.RecipeViewModel

@Composable
fun RecipesApp() {
    var currentScreen by remember {
        mutableStateOf(ScreenId.CATEGORIES)
    }
    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }
    RecipeComposeAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface,

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
                        onRecipesClick = {
                            if (selectedCategoryId != null) currentScreen =
                                ScreenId.RECIPES else println("ERROR")
                        }
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    when (currentScreen) {
                        ScreenId.CATEGORIES -> CategoriesScreen(
                            viewModel = CategoriesViewModel(),
                            modifier = Modifier,
                            onCategoryClick = { category: CategoryUiModel ->
                                selectedCategoryId = category.id
                                currentScreen = ScreenId.RECIPES
                            })

                        ScreenId.FAVORITES -> FavoritesScreen()
                        ScreenId.RECIPES -> RecipesScreen(
                            categoryId = selectedCategoryId,
                            modifier = Modifier,
                            viewModel = RecipeViewModel(),
                            onRecipeClick = {}

                        )
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