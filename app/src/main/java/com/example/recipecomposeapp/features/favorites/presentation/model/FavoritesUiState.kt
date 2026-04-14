package com.example.recipecomposeapp.features.favorites.presentation.model

import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel

data class FavoritesUiState(
    val favoriteRecipes: List<RecipesUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val isEmpty: Boolean
        get() = favoriteRecipes.isEmpty() && !isLoading && error == null

    val hasError: Boolean
        get() = error != null
}
