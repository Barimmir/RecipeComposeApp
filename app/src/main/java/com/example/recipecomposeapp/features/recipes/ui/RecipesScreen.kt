package com.example.recipecomposeapp.features.recipes.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesViewModel

@Composable
fun RecipesScreen(
    viewModel: RecipesViewModel,
    onRecipeClick: (Int, RecipesUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val imagePainter = rememberAsyncImagePainter(
            model = uiState.categoryImageUrl,
            placeholder = painterResource(R.drawable.bcg_recipes_list),
            error = painterResource(R.drawable.bcg_recipes_list)
        )
        ScreenHeader(
            title = uiState.categoryTitle.uppercase(),
            imagePainter = imagePainter,
            contentDescription = "Шапка рецептов",
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
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Ошибка: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(Dimens.SIXTEEN_DP))
                        Button(onClick = { viewModel.refresh() }) {
                            Text("Повторить")
                        }
                        Spacer(modifier = Modifier.height(Dimens.EIGHT_DP))
                        Button(onClick = { viewModel.clearError() }) {
                            Text("Закрыть")
                        }
                    }
                }
            }

            uiState.isEmpty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Нет рецептов в этой категории")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimens.SIXTEEN_DP),
                    verticalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP)
                ) {
                    items(
                        items = uiState.recipes,
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

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview_Variant1() {
    RecipeComposeAppTheme {
        
        val mockRecipes = listOf(
            RecipesUiModel(
                id = 1,
                title = "Классический бургер",
                imageUrl = "file:///android_asset/burger_hamburger.png",
                ingredients = emptyList(),
                method = "Приготовление...",
                isFavorite = false
            ),
            RecipesUiModel(
                id = 2,
                title = "Пицца Маргарита",
                imageUrl = "file:///android_asset/pizza.png",
                ingredients = emptyList(),
                method = "Приготовление...",
                isFavorite = true
            ),
            RecipesUiModel(
                id = 3,
                title = "Чизкейк Нью-Йорк",
                imageUrl = "file:///android_asset/cheesecake.png",
                ingredients = emptyList(),
                method = "Приготовление...",
                isFavorite = false
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            ScreenHeader(
                title = "БУРГЕРЫ",
                imagePainter = painterResource(id = R.drawable.bcg_recipes_list),
                contentDescription = "Шапка рецептов",
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