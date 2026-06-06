package com.example.recipecomposeapp.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.dao.CategoryDao
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryImpl
import com.example.recipecomposeapp.data.network.api.RecipesApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import kotlinx.serialization.json.Json

@RunWith(AndroidJUnit4::class)
class CompleteDataFlowTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var database: RecipesDatabase
    private lateinit var repository: RecipesRepositoryImpl
    private lateinit var categoryDao: CategoryDao
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java).build()
        categoryDao = database.categoryDao()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        val apiService = retrofit.create(RecipesApiService::class.java)

        repository = RecipesRepositoryImpl(apiService, database)
    }

    @After
    fun tearDown() {
        database.close()
        mockWebServer.shutdown()
    }

    @Test
    fun fullDataFlow_cachesDataInRoom() = runBlocking {
        val jsonResponse = """
            [
                {"id": 1, "title": "Desserts", "description": "Sweet treats", "imageUrl": "https://example.com/desserts.jpg"},
                {"id": 2, "title": "Main Course", "description": "Hearty meals", "imageUrl": "https://example.com/main.jpg"}
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json")
        )

        repository.getCategories().test {
            awaitItem()
            delay(500)
            val cachedCategories = categoryDao.getAllCategories().first()
            assertEquals(2, cachedCategories.size)
            assertEquals("Desserts", cachedCategories[0].name)
            assertEquals("Main Course", cachedCategories[1].name)
            cancelAndIgnoreRemainingEvents()
        }
    }
}