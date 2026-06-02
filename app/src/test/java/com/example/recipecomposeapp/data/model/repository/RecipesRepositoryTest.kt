package com.example.recipecomposeapp.data.model.repository

import app.cash.turbine.test
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.dao.CategoryDao
import com.example.recipecomposeapp.data.database.dao.RecipeDao
import com.example.recipecomposeapp.data.model.IngredientDto
import com.example.recipecomposeapp.data.network.api.RecipesApiService
import com.example.recipecomposeapp.fixtures.CategoryTestFixtures
import com.example.recipecomposeapp.fixtures.RecipeTestFixtures
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecipesRepositoryTest {

    private val apiService: RecipesApiService = mockk(relaxed = true)
    private val categoryDao: CategoryDao = mockk(relaxed = true)
    private val recipeDao: RecipeDao = mockk(relaxed = true)
    private val database: RecipesDatabase = mockk(relaxed = true)

    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setUp() {
        every { database.categoryDao() } returns categoryDao
        every { database.recipeDao() } returns recipeDao
        repository = RecipesRepositoryImpl(apiService, database)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun getCategories_emitsCategoriesFromDatabase() = runTest {
        val categoryEntities = CategoryTestFixtures.createCategoryEntityList()
        every { categoryDao.getAllCategories() } returns flowOf(categoryEntities)

        repository.getCategories().test {
            val result = awaitItem()
            val expected = listOf(
                CategoryTestFixtures.createCategoryDto(
                    id = 1,
                    title = "Category 1",
                    description = "Description 1",
                    imageUrl = "http://example.com/cat1.jpg"
                ),
                CategoryTestFixtures.createCategoryDto(
                    id = 2,
                    title = "Category 2",
                    description = "Description 2",
                    imageUrl = "http://example.com/cat2.jpg"
                )
            )
            assertEquals(expected, result)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getCategories_stillEmitsDataWhenApiThrowsException() = runTest {
        val categoryEntities = listOf(
            CategoryTestFixtures.createCategoryEntity(
                id = 1,
                name = "Category From DB",
                description = "Description",
                imageUrl = "http://example.com/cat.jpg"
            )
        )
        every { categoryDao.getAllCategories() } returns flowOf(categoryEntities)
        coEvery { apiService.getCategories() } throws RuntimeException("Network error")

        repository.getCategories().test {
            val result = awaitItem()
            val expected = listOf(
                CategoryTestFixtures.createCategoryDto(
                    id = 1,
                    title = "Category From DB",
                    description = "Description",
                    imageUrl = "http://example.com/cat.jpg"
                )
            )
            assertEquals(expected, result)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun getRecipesByCategory_returnsFlowFilteredByCategoryId() = runTest {
        val recipeEntities = listOf(
            RecipeTestFixtures.createRecipeEntity(
                id = 1,
                title = "Recipe 1",
                categoryId = 1,
                imageUrl = "http://example.com/recipe1.jpg",
                ingredients = listOf("Ingredient 1", "Ingredient 2"),
                method = listOf("Step 1", "Step 2")
            ),
            RecipeTestFixtures.createRecipeEntity(
                id = 2,
                title = "Recipe 2",
                categoryId = 1,
                imageUrl = "http://example.com/recipe2.jpg",
                ingredients = listOf("Ingredient A"),
                method = listOf("Step A")
            )
        )
        every { recipeDao.getRecipesByCategory(1) } returns flowOf(recipeEntities)

        repository.getRecipesByCategory(1).test {
            val result = awaitItem()
            val expected = listOf(
                RecipeTestFixtures.createRecipeDto(
                    id = 1,
                    title = "Recipe 1",
                    ingredients = listOf(
                        IngredientDto("", "", "Ingredient 1"),
                        IngredientDto("", "", "Ingredient 2")
                    ),
                    method = listOf("Step 1", "Step 2"),
                    imageUrl = "http://example.com/recipe1.jpg"
                ),
                RecipeTestFixtures.createRecipeDto(
                    id = 2,
                    title = "Recipe 2",
                    ingredients = listOf(IngredientDto("", "", "Ingredient A")),
                    method = listOf("Step A"),
                    imageUrl = "http://example.com/recipe2.jpg"
                )
            )
            assertEquals(expected, result)
            cancelAndIgnoreRemainingEvents()
        }
    }
}