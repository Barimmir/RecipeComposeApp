package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.ui.theme.recipes.model.IngredientUiModel

data class IngredientDto(
    val quantity: String,
    val unitOfMeasure: String,
    val description: String,
)

fun IngredientDto.toUiModel() = IngredientUiModel(
    name = description,
    amount = quantity,
)