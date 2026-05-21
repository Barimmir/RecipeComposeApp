package com.example.recipecomposeapp.data.model.repository

import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    fun getCategories(): Flow<List<CategoryDto>>
    fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>>
    fun getRecipe(recipeId: Int): Flow<RecipeDto?>
}