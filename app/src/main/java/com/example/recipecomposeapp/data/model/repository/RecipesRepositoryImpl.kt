package com.example.recipecomposeapp.data.model.repository

import android.util.Log
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.dao.CategoryDao
import com.example.recipecomposeapp.data.database.dao.RecipeDao
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import com.example.recipecomposeapp.data.model.toDto
import com.example.recipecomposeapp.data.model.toEntity
import com.example.recipecomposeapp.features.core.network.api.RecipesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecipesRepositoryImpl(
    private val apiService: RecipesApiService,
    private val database: RecipesDatabase
) : RecipesRepository {
    private val categoryDao: CategoryDao = database.categoryDao()
    private val recipeDao: RecipeDao = database.recipeDao()
    override fun getCategories(): Flow<List<CategoryDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = apiService.getCategories()
                val entities = categories.map { it.toEntity() }
                categoryDao.insertAllCategories(entities)
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки категорий!", e)
            }
        }
        return categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDto() }
        }
    }

    override fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val recipes = apiService.getRecipesByCategory(categoryId)
                val entities = recipes.map { it.toEntity(categoryId) }
                recipeDao.insertAllRecipes(entities)
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки рецептов по категории $categoryId", e)
            }
        }
        return recipeDao.getRecipesByCategory(categoryId).map { entities ->
            entities.map { it.toDto() }
        }
    }

    override fun getRecipe(recipeId: Int): Flow<RecipeDto?> {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val recipe = apiService.getRecipe(recipeId)
                val existing = recipeDao.getRecipeById(recipeId).first()
                val categoryId = existing?.categoryId ?: 0
                recipeDao.insertAllRecipes(listOf(recipe.toEntity(categoryId)))
            } catch (e: Exception) {
                Log.e("RecipesRepository", "Ошибка загрузки рецепта $recipeId", e)
            }
        }
        return recipeDao.getRecipeById(recipeId).map { entity -> entity?.toDto() }
    }
}