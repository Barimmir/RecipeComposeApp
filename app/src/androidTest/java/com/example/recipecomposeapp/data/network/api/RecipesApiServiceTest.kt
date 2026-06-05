package com.example.recipecomposeapp.data.network.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import retrofit2.Retrofit

@RunWith(AndroidJUnit4::class)
class RecipesApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: RecipesApiService
    private val json = Json { ignoreUnknownKeys = true }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        apiService = retrofit.create(RecipesApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getCategories_parsesJsonCorrectly() = runTest {
        val responseJson = """
            [
                {"id": 1, "title": "Desserts", "description": "Sweet treats", "imageUrl": "desserts.jpg"},
                {"id": 2, "title": "Main Course", "description": "Hearty meals", "imageUrl": "main.jpg"}
            ]
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(responseJson).setResponseCode(200))

        val result = apiService.getCategories()

        assertEquals(2, result.size)
        assertEquals("Desserts", result[0].title)
        assertEquals("Sweet treats", result[0].description)
        assertEquals("Main Course", result[1].title)
        assertEquals("Hearty meals", result[1].description)
    }

    @Test
    fun getRecipeById_parsesJsonCorrectly() = runTest {
        val responseJson = """
            {
                "id": 1,
                "title": "Chocolate Cake",
                "ingredients": [
                    {"quantity": "200", "unitOfMeasure": "g", "description": "flour"},
                    {"quantity": "2", "unitOfMeasure": "pieces", "description": "eggs"}
                ],
                "method": ["Preheat oven", "Mix ingredients", "Bake for 30 min"],
                "imageUrl": "cake.jpg"
            }
        """.trimIndent()
        mockWebServer.enqueue(MockResponse().setBody(responseJson).setResponseCode(200))

        val result = apiService.getRecipe(1)

        assertEquals("Chocolate Cake", result.title)
        assertEquals(2, result.ingredients.size)
        assertEquals("flour", result.ingredients[0].description)
        assertEquals("200", result.ingredients[0].quantity)
        assertEquals("g", result.ingredients[0].unitOfMeasure)
        assertEquals(3, result.method.size)
        assertEquals("Preheat oven", result.method[0])
        assertEquals("Mix ingredients", result.method[1])
        assertEquals("Bake for 30 min", result.method[2])
    }
}