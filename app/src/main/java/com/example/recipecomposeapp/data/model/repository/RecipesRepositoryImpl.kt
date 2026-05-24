package com.example.recipecomposeapp.data.model.repository

import android.util.Log
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.dao.CategoryDao
import com.example.recipecomposeapp.data.database.dao.RecipeDao
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import com.example.recipecomposeapp.data.model.toDto
import com.example.recipecomposeapp.data.model.toEntity
import com.example.recipecomposeapp.data.network.api.RecipesApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class RecipesRepositoryImpl(
    private val apiService: RecipesApiService,
    private val database: RecipesDatabase
) : RecipesRepository {
    private val categoryDao: CategoryDao = database.categoryDao()
    private val recipeDao: RecipeDao = database.recipeDao()

    private val ioScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    override fun getCategories(): Flow<List<CategoryDto>> {
        return categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDto() }
        }.onStart {
            ioScope.launch {
                try {
                    val categories = apiService.getCategories()
                    val entities = categories.map { it.toEntity() }
                    categoryDao.insertAllCategories(entities)
                } catch (e: Exception) {
                    Log.e("RecipesRepository", "Error loading categories", e)
                }
            }
        }
    }

    override fun getRecipesByCategory(categoryId: Int): Flow<List<RecipeDto>> {
        return recipeDao.getRecipesByCategory(categoryId).map { entities ->
            entities.map { it.toDto() }
        }.onStart {
            ioScope.launch {
                try {
                    val recipes = apiService.getRecipesByCategory(categoryId)
                    val entities = recipes.map { it.toEntity(categoryId) }
                    recipeDao.insertAllRecipes(entities)
                } catch (e: Exception) {
                    Log.e("RecipesRepository", "Error loading recipes for category $categoryId", e)
                }
            }
        }
    }

    override fun getRecipe(recipeId: Int): Flow<RecipeDto?> {
        return recipeDao.getRecipeById(recipeId).map { entity -> entity?.toDto() }
            .onStart {
                ioScope.launch {
                    try {
                        val recipe = apiService.getRecipe(recipeId)
                        val existing = recipeDao.getRecipeById(recipeId).first()
                        val categoryId = existing?.categoryId ?: 0
                        recipeDao.insertAllRecipes(listOf(recipe.toEntity(categoryId)))
                    } catch (e: Exception) {
                        Log.e("RecipesRepository", "Error loading recipe $recipeId", e)
                    }
                }
            }
    }

    override fun getRecipesByIds(ids: List<Int>): Flow<List<RecipeDto>> {
        return recipeDao.getRecipesByIds(ids).map { entities ->
            entities.map { it.toDto() }
        }
    }
}