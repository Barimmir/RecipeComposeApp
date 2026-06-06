package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesViewModel
import com.example.recipecomposeapp.features.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme


@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel,
    modifier: Modifier = Modifier,
    onCategoryClick: (Int, String, String) -> Unit
) {
    val categories by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("categories_screen")
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        ScreenHeader(
            "Категории".uppercase(),
            imagePainter = painterResource(id = R.drawable.bcg_categories),
            contentDescription = "Шапка категорий",
            showShareButton = true,
            onShareClick = {},
            isFavorite = false,
            showFavoriteButton = false,
            onFavoriteClick = {}
        )
        CategoriesContent(
            uiState = categories,
            onCategoryClick = { id ->
                categories.categories.find { it.id == id }?.let { cat ->
                    onCategoryClick(cat.id, cat.title, cat.imageUrl)
                }
            },
            modifier = Modifier
                .weight(Dimens.WEIGHT_ONE_F)
                .fillMaxWidth()
        )
    }
}

@Composable
fun CategoriesContent(
    uiState: CategoriesUiState,
    onCategoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.testTag("loading_indicator")
                    )
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error,
                        modifier = Modifier.testTag("error_message")
                    )
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .testTag("categories_grid")
                        .weight(Dimens.WEIGHT_ONE_F)
                        .fillMaxWidth()
                        .padding(Dimens.EIGHT_DP),
                    contentPadding = PaddingValues(Dimens.EIGHT_DP),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP),
                    verticalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP)
                ) {
                    items(uiState.categories, key = { it.id }) { category ->
                        CategoryItem(
                            id = category.id,
                            title = category.title,
                            descriptionCategory = category.description,
                            imageUrl = category.imageUrl,
                            onClick = { onCategoryClick(category.id) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen(
            viewModel = hiltViewModel(),
            modifier = Modifier,
            onCategoryClick = { _, _, _ -> }
        )
    }
}
