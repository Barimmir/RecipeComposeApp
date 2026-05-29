package com.example.recipecomposeapp.features.favorites.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.recipes.ui.RecipeItem
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel

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
        FavoritesScreen(
            viewModel = hiltViewModel(),
            onRecipeClick = { _, _ -> },
            modifier = Modifier
        )
    }
}
