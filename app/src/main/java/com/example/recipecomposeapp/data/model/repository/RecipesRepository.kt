package com.example.recipecomposeapp.data.model.repository

import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto

interface RecipesRepository {
    suspend fun getCategories(): List<CategoryDto>
    suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto>
    suspend fun getRecipe(recipeId: Int): RecipeDto?
}