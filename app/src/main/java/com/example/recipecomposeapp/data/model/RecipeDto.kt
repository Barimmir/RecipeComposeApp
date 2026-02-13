package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.ui.theme.recipes.model.RecipeUiModel

data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: String,
    val imageUrl: String,
)

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients.map { it.toUiModel() },
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl,
    method = method,
    isFavorite = false
)