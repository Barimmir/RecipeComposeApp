package com.example.recipecomposeapp.features.favorites.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.recipes.ui.RecipeItem
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    onRecipeClick: (Int, RecipesUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
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

            uiState.hasError -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.SIXTEEN_DP),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.error ?: "Произошла ошибка",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.clearError() }) {
                            Text("Закрыть")
                        }
                    }
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
                method = "Приготовление...",
                isFavorite = true
            ),
            RecipesUiModel(
                id = 2,
                title = "Пицца Маргарита",
                imageUrl = "file:///android_asset/pizza.png",
                ingredients = emptyList(),
                method = "Приготовление...",
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