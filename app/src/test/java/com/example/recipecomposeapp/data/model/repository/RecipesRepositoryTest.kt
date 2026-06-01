package com.example.recipecomposeapp.data.model.repository

import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.dao.CategoryDao
import com.example.recipecomposeapp.data.database.dao.RecipeDao
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.data.database.entity.RecipeEntity
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.data.model.RecipeDto
import com.example.recipecomposeapp.data.network.api.RecipesApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RecipesRepositoryTest {

    private val apiService: RecipesApiService = mock()
    private val categoryDao: CategoryDao = mock()
    private val recipeDao: RecipeDao = mock()
    private val database: RecipesDatabase = mock()

    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setUp() {
        whenever(database.categoryDao()).thenReturn(categoryDao)
        whenever(database.recipeDao()).thenReturn(recipeDao)
        repository = RecipesRepositoryImpl(apiService, database)
    }

    @Test
    fun getCategories_emitsCategoriesFromDatabase() = runTest {
        val categoryEntities = listOf(
            CategoryEntity(
                id = 1,
                name = "Category 1",
                description = "Description 1",
                imageUrl = "http://example.com/cat1.jpg"
            ),
            CategoryEntity(
                id = 2,
                name = "Category 2",
                description = "Description 2",
                imageUrl = "http://example.com/cat2.jpg"
            )
        )
        whenever(categoryDao.getAllCategories()).thenReturn(flowOf(categoryEntities))

        val result = repository.getCategories().first()

        val expected = listOf(
            CategoryDto(
                id = 1,
                title = "Category 1",
                description = "Description 1",
                imageUrl = "http://example.com/cat1.jpg"
            ),
            CategoryDto(
                id = 2,
                title = "Category 2",
                description = "Description 2",
                imageUrl = "http://example.com/cat2.jpg"
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun getCategories_stillEmitsDataWhenApiThrowsException() = runTest {
        val categoryEntities = listOf(
            CategoryEntity(
                id = 1,
                name = "Category From DB",
                description = "Description",
                imageUrl = "http://example.com/cat.jpg"
            )
        )
        whenever(categoryDao.getAllCategories()).thenReturn(flowOf(categoryEntities))
        whenever(apiService.getCategories()).thenThrow(RuntimeException("Network error"))

        val result = repository.getCategories().first()

        val expected = listOf(
            CategoryDto(
                id = 1,
                title = "Category From DB",
                description = "Description",
                imageUrl = "http://example.com/cat.jpg"
            )
        )
        assertEquals(expected, result)
    }

    @Test
    fun getRecipesByCategory_returnsFlowFilteredByCategoryId() = runTest {
        val recipeEntities = listOf(
            RecipeEntity(
                id = 1,
                title = "Recipe 1",
                categoryId = 1,
                imageUrl = "http://example.com/recipe1.jpg",
                ingredients = listOf("Ingredient 1", "Ingredient 2"),
                method = listOf("Step 1", "Step 2")
            ),
            RecipeEntity(
                id = 2,
                title = "Recipe 2",
                categoryId = 1,
                imageUrl = "http://example.com/recipe2.jpg",
                ingredients = listOf("Ingredient A"),
                method = listOf("Step A")
            )
        )
        whenever(recipeDao.getRecipesByCategory(1)).thenReturn(flowOf(recipeEntities))

        val result = repository.getRecipesByCategory(1).first()

        val expected = listOf(
            RecipeDto(
                id = 1,
                title = "Recipe 1",
                ingredients = listOf(
                    IngredientDto("", "", "Ingredient 1"),
                    IngredientDto("", "", "Ingredient 2")
                ),
                method = listOf("Step 1", "Step 2"),
                imageUrl = "http://example.com/recipe1.jpg"
            ),
            RecipeDto(
                id = 2,
                title = "Recipe 2",
                ingredients = listOf(IngredientDto("", "", "Ingredient A")),
                method = listOf("Step A"),
                imageUrl = "http://example.com/recipe2.jpg"
            )
        )
        assertEquals(expected, result)
    }
}