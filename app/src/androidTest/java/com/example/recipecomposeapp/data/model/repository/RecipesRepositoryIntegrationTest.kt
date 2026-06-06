package com.example.recipecomposeapp.data.model.repository

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.dao.CategoryDao
import com.example.recipecomposeapp.data.database.dao.RecipeDao
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.network.api.RecipesApiService
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RecipesRepositoryIntegrationTest {

    private lateinit var database: RecipesDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var recipeDao: RecipeDao
    private lateinit var apiService: RecipesApiService
    private lateinit var repository: RecipesRepositoryImpl

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java).build()
        categoryDao = database.categoryDao()
        recipeDao = database.recipeDao()
        apiService = mockk(relaxed = true)
        repository = RecipesRepositoryImpl(apiService, database)
    }

    @After
    fun tearDown() {
        database.close()
        clearAllMocks()
    }

    @Test
    fun savesDataToCacheAfterSuccessfulApiCall() = runBlocking {
        val categoryDtos = listOf(
            CategoryDto(1, "Desserts", "Sweet treats", "https://example.com/desserts.jpg"),
            CategoryDto(2, "Main Course", "Hearty meals", "https://example.com/main.jpg")
        )
        coEvery { apiService.getCategories() } returns categoryDtos

        repository.getCategories().test {
            awaitItem()
            delay(500)
            val cachedCategories = categoryDao.getAllCategories().first()
            assertEquals(2, cachedCategories.size)
            assertEquals("Desserts", cachedCategories[0].name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun returnsCachedDataWhenApiFails() = runTest {
        val cachedEntities = listOf(
            CategoryEntity(1, "Appetizers", "Start your meal right", "https://example.com/app.jpg"),
            CategoryEntity(2, "Salads", "Fresh and healthy", "https://example.com/salads.jpg")
        )
        categoryDao.insertAllCategories(cachedEntities)

        coEvery { apiService.getCategories() } throws IOException("Network error")

        repository.getCategories().test {
            val firstEmission = awaitItem()
            assertEquals(2, firstEmission.size)
            assertEquals("Appetizers", firstEmission[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
