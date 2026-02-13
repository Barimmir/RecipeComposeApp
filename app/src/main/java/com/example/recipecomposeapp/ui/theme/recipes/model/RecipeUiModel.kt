package com.example.recipecomposeapp.ui.theme.recipes.model

import androidx.compose.runtime.Immutable

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientUiModel>,
    val method: String,
    val isFavorite: Boolean
)