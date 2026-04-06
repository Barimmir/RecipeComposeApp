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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.data.model.toUiModel
import com.example.recipecomposeapp.features.core.utils.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.ui.RecipeItem
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipeUiModel
import com.example.recipecomposeapp.features.core.utils.FavoriteDataStoreManager
import kotlinx.coroutines.flow.map

@Composable
fun FavoritesScreen(
    recipesRepository: RecipesRepositoryStub,
    favoriteDataStoreManager: FavoriteDataStoreManager,
    onRecipeClick: (Int, RecipeUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteIdsFlow = favoriteDataStoreManager.getFavoriteIdsFlow()
    val favoriteRecipes by remember {
        favoriteIdsFlow.map { ids ->
            ids.mapNotNull { id ->
                val intId = id.toIntOrNull()
                intId?.let {
                    recipesRepository.getRecipeById(it)
                }
            }
        }
    }.collectAsState(initial = emptyList())
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
        if (favoriteRecipes.isEmpty()) {
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
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.SIXTEEN_DP),
                verticalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP)
            ) {
                items(
                    items = favoriteRecipes,
                    key = { it.id }
                ) { recipe ->
                    RecipeItem(
                        recipe = recipe.toUiModel(),
                        onRecipeClick = onRecipeClick
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    val context = LocalContext.current
    RecipeComposeAppTheme {
        FavoritesScreen(
            recipesRepository = RecipesRepositoryStub,
            favoriteDataStoreManager = FavoriteDataStoreManager(context),
            onRecipeClick = { _, _ -> },
            modifier = Modifier

        )
    }
}