package com.example.recipecomposeapp.features.categories.ui

import android.annotation.SuppressLint
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesViewModel

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel,
    onCategoryClick: (Int, String, String) -> Unit
) {

    val categories by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        when {
            categories.isLoading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Загрузка категорий...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            categories.error != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Ошибка загрузки: ${categories.error}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {
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
    }
}


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen(
            viewModel = CategoriesViewModel(),
            modifier = Modifier,
            onCategoryClick = { id, title, imageUrl ->
                println("Clicked: id=$id, title=$title, imageUrl=$imageUrl")
            }
        )
    }
}
