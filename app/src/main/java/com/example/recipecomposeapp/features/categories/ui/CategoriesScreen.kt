package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.app.di.CategoriesViewModelFactory
import com.example.recipecomposeapp.app.di.RecipeApplication
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.core.ui.ScreenHeader


@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    onCategoryClick: (Int, String, String) -> Unit
) {
    val appContainer = (LocalContext.current.applicationContext as RecipeApplication).appContainer
    val viewModel = remember { CategoriesViewModelFactory(appContainer.recipesRepository).create() }
    val categories by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = modifier
            .fillMaxSize()
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(Dimens.WEIGHT_ONE_F)
                .fillMaxWidth()
                .padding(Dimens.EIGHT_DP),
            contentPadding = PaddingValues(Dimens.EIGHT_DP),
            horizontalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP),
            verticalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP)
        ) {
            items(categories.categories, key = { it.id }) { categories ->
                CategoryItem(
                    id = categories.id,
                    title = categories.title,
                    descriptionCategory = categories.description,
                    imageUrl = categories.imageUrl,
                    onClick = {
                        onCategoryClick(
                            categories.id,
                            categories.title,
                            categories.imageUrl
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen(
            modifier = Modifier,
            onCategoryClick = { _, _, _ -> }
        )
    }
}
