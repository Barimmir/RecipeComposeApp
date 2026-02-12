package com.example.recipecomposeapp.ui.theme.recipes.model

import androidx.compose.runtime.Immutable
import com.example.recipecomposeapp.data.model.IngredientDto

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<IngredientDto>,
    val method: String,
    val isFavorite: Boolean
)