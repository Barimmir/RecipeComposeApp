package com.example.recipecomposeapp.ui.theme.recipes.model

import androidx.compose.runtime.Immutable

@Immutable
data class IngredientUiModel(
    val name: String,
    val amount: String
)