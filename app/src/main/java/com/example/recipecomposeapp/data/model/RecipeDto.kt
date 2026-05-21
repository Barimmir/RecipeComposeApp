package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.data.database.entity.RecipeEntity
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

fun RecipeDto.toEntity(categoryId: Int) = RecipeEntity(
    id = id,
    title = title,
    categoryId = categoryId,
    imageUrl = imageUrl,
    ingredients = ingredients.map { it.description },
    method = method
)

fun RecipeEntity.toDto() = RecipeDto(
    id = id,
    title = title,
    ingredients = ingredients.map { IngredientDto("", "", it) },
    method = method,
    imageUrl = imageUrl
)