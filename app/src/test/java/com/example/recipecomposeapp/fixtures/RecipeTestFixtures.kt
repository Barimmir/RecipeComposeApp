package com.example.recipecomposeapp.fixtures

import com.example.recipecomposeapp.data.database.entity.RecipeEntity
import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.data.model.RecipeDto

object RecipeTestFixtures {

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

    fun createRecipeEntity(
        id: Int = 1,
        title: String = "Recipe 1",
        categoryId: Int = 1,
        imageUrl: String = "http://example.com/recipe1.jpg",
        ingredients: List<String> = listOf("Ingredient 1", "Ingredient 2"),
        method: List<String> = listOf("Step 1", "Step 2")
    ) = RecipeEntity(
        id = id,
        title = title,
        categoryId = categoryId,
        imageUrl = imageUrl,
        ingredients = ingredients,
        method = method
    )

    fun createRecipeEntityList() = listOf(
        createRecipeEntity(id = 1, title = "Recipe 1"),
        createRecipeEntity(id = 2, title = "Recipe 2")
    )
}
