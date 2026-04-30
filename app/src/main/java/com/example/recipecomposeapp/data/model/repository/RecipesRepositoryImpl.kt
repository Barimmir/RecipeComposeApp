package com.example.recipecomposeapp.data.model.repository

import android.util.Log
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import com.example.recipecomposeapp.features.core.network.api.RecipesApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepositoryImpl(
    private val apiService: RecipesApiService
) : RecipesRepository {
    override suspend fun getCategories(): List<CategoryDto> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getCategories()
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки категорий!", e)
                emptyList()
            }
        }
    }

    override suspend fun getRecipesByCategory(categoryId: Int): List<RecipeDto> {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getRecipesByCategory(categoryId)
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки рецептов по категории $categoryId", e)
                emptyList()
            }
        }
    }

    override suspend fun getRecipe(recipeId: Int): RecipeDto? {
        return withContext(Dispatchers.IO){
            try {
                apiService.getRecipe(recipeId)
            } catch (e: Exception){
                Log.e("RecipesRepository", "Ошибка загрузки рецепта $recipeId", e)
                throw e
            }
        }
    }
}