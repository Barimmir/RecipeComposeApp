package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.features.core.utils.Constants
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientDto>,
    val method: List<String>,
    val imageUrl: String,
)

fun RecipeDto.toUiModel() = RecipesUiModel(
    id = id,
    title = title,
    ingredients = ingredients.map { it.toUiModel() },
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.IMAGES_BASE_URL + imageUrl,
    method = method,
    isFavorite = false
)