package com.example.recipecomposeapp.ui.theme.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.ui.theme.ScreenHeader
import com.example.recipecomposeapp.ui.theme.recipes.components.RecipeItem
import com.example.recipecomposeapp.ui.theme.recipes.model.RecipeViewModel

@Composable
fun RecipesScreen(
    categoryId: Int,
    modifier: Modifier = Modifier,
    viewModel: RecipeViewModel
) {
    val recipes by viewModel.recipes.collectAsState()
    LaunchedEffect(categoryId) {
        categoryId.let {
            viewModel.loadRecipes(categoryId)
        }
    }
    val categoryTitle = when (categoryId) {
        0 -> "Бургеры"
        1 -> "Десерты"
        2 -> "Пицца"
        3 -> "Рыба"
        4 -> "Супы"
        5 -> "Салаты"
        else -> "Рецепты"
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        ScreenHeader(
            title = categoryTitle,
            imagePainter = painterResource(id = R.drawable.bcg_recipes_list),
            contentDescription = "Шапка рецептов"
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = recipes, key = { it.id }) { recipe ->
                RecipeItem(
                    id = recipe.id,
                    title = recipe.title,
                    ingredients = recipe.ingredients,
                    method = recipe.method,
                    imageUrl = recipe.imageUrl,
                    onRecipeClick = {}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    RecipeComposeAppTheme {
        RecipesScreen(0, viewModel = RecipeViewModel())
    }
}