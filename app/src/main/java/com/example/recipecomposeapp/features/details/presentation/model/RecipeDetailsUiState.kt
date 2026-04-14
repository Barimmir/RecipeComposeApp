package com.example.recipecomposeapp.features.details.presentation.model

import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientsUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel

data class RecipeDetailsUiState(
    val recipe: RecipesUiModel? = null,
    val numberOfServings: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val scaledIngredients: List<IngredientsUiModel>
        get() = recipe?.ingredients?.map { ingredient ->
            ingredient.copy(
                amount = ingredient.amount * numberOfServings
            )
        } ?: emptyList()

    val hasError: Boolean
        get() = error != null
}
