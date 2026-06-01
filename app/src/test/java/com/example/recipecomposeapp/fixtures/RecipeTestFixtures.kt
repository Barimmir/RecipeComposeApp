package com.example.recipecomposeapp.fixtures

import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.data.model.RecipeDto

fun createIngredientDto(
    quantity: String = "1",
    unitOfMeasure: String = "шт",
    description: String = "Тестовый ингредиент"
) = IngredientDto(
    quantity = quantity,
    unitOfMeasure = unitOfMeasure,
    description = description
)

fun createRecipeDto(
    id: Int = 1,
    title: String = "Тестовый рецепт",
    ingredients: List<IngredientDto> = listOf(createIngredientDto()),
    method: List<String> = listOf("Шаг 1", "Шаг 2"),
    imageUrl: String = "test.png"
) = RecipeDto(
    id = id,
    title = title,
    ingredients = ingredients,
    method = method,
    imageUrl = imageUrl
)

fun createRecipeDtoList() = listOf(
    createRecipeDto(id = 1, title = "Рецепт 1"),
    createRecipeDto(id = 2, title = "Рецепт 2")
)