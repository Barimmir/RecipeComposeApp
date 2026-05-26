package com.example.recipecomposeapp.features.favorites.ui

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipecomposeapp.app.di.FavoritesViewModelFactory
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.recipes.ui.RecipeItem
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel

@Composable
fun FavoritesScreen(
    onRecipeClick: (Int, RecipesUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel = remember { FavoritesViewModelFactory(application).create() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        ScreenHeader(
            "Избранные".uppercase(),
            imagePainter = painterResource(id = R.drawable.bcg_favorites),
            contentDescription = "Шапка избранных",
            showShareButton = true,
            onShareClick = {},
            isFavorite = false,
            showFavoriteButton = false,
            onFavoriteClick = {}
        )
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.isEmpty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.SIXTEEN_DP),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "У вас пока нет избранных рецептов",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimens.SIXTEEN_DP),
                    verticalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP)
                ) {
                    items(
                        items = uiState.favoriteRecipes,
                        key = { it.id }
                    ) { recipe ->
                        RecipeItem(
                            recipe = recipe,
                            onRecipeClick = onRecipeClick
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    RecipeComposeAppTheme {
        val mockRecipes = listOf(
            RecipesUiModel(
                id = 1,
                title = "Классический бургер",
                imageUrl = "file:///android_asset/burger_hamburger.png",
                ingredients = emptyList(),
                method = listOf("Приготовление..."),
                isFavorite = true
            ),
            RecipesUiModel(
                id = 2,
                title = "Пицца Маргарита",
                imageUrl = "file:///android_asset/pizza.png",
                ingredients = emptyList(),
                method = listOf("Приготовление..."),
                isFavorite = true
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            ScreenHeader(
                "ИЗБРАННЫЕ",
                imagePainter = painterResource(id = R.drawable.bcg_favorites),
                contentDescription = "Шапка избранных",
                showShareButton = true,
                onShareClick = {},
                isFavorite = false,
                showFavoriteButton = false,
                onFavoriteClick = {}
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.SIXTEEN_DP),
                verticalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP)
            ) {
                items(
                    items = mockRecipes,
                    key = { it.id }
                ) { recipe ->
                    RecipeItem(
                        recipe = recipe,
                        onRecipeClick = { _, _ -> }
                    )
                }
            }
        }
    }
}