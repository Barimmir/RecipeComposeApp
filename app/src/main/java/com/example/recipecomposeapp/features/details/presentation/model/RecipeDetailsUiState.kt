package com.example.recipecomposeapp.features.details.presentation.model

import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientsUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel

data class RecipeDetailsUiState(
    val recipe: RecipesUiModel? = null,
    val currentPortions: Int = 1,
    val scaledIngredients: List<IngredientsUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val hasError: Boolean
        get() = error != null
}
