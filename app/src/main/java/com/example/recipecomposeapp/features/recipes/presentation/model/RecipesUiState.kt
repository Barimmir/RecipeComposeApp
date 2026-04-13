package com.example.recipecomposeapp.features.recipes.presentation.model

data class RecipesUiState(
    val recipes: List<RecipesUiModel> = emptyList(),
    val categoryTitle: String = "",
    val categoryImageUrl: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isEmpty: Boolean
        get() = recipes.isEmpty() && !isLoading && error == null

    val hasError: Boolean
        get() = error != null
}