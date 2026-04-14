package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientsUiModel

data class IngredientDto(
    val quantity: Float,
    val unitOfMeasure: String,
    val description: String,
)

fun IngredientDto.toUiModel() = IngredientsUiModel(
    name = description,
    amount = quantity,
    unitOfMeasure = unitOfMeasure
)