package com.example.recipecomposeapp.data.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.data.database.entity.RecipeEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@RunWith(AndroidJUnit4::class)
class RecipesDaoTest {

    private lateinit var context: Context
    private lateinit var database: RecipesDatabase
    private lateinit var categoryDao: CategoryDao
    private lateinit var recipeDao: RecipeDao

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        categoryDao = database.categoryDao()
        recipeDao = database.recipeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertsAndRetrievesCategories() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Breakfast", description = "Morning meals", imageUrl = "breakfast.jpg"),
            CategoryEntity(id = 2, name = "Lunch", description = "Midday meals", imageUrl = "lunch.jpg"),
            CategoryEntity(id = 3, name = "Dinner", description = "Evening meals", imageUrl = "dinner.jpg")
        )
        categoryDao.insertAllCategories(categories)
        val result = categoryDao.getAllCategories().first()
        assert(result.size == 3)
    }

    @Test
    fun insertReplacesDuplicateCategory() = runTest {
        val category1 = CategoryEntity(id = 1, name = "Breakfast", description = "Morning meals", imageUrl = "breakfast.jpg")
        val category2 = CategoryEntity(id = 1, name = "Brunch", description = "Late morning meals", imageUrl = "brunch.jpg")
        categoryDao.insertAllCategories(listOf(category1))
        categoryDao.insertAllCategories(listOf(category2))
        val result = categoryDao.getAllCategories().first()
        assert(result.size == 1)
        assert(result[0].name == "Brunch")
    }

    @Test
    fun getRecipesByCategoryReturnsCorrectItems() = runTest {
        val recipes = listOf(
            RecipeEntity(id = 1, title = "Pancakes", categoryId = 1, imageUrl = "pancakes.jpg", ingredients = listOf("flour", "eggs"), method = listOf("mix", "cook")),
            RecipeEntity(id = 2, title = "Salad", categoryId = 2, imageUrl = "salad.jpg", ingredients = listOf("lettuce", "tomato"), method = listOf("cut", "mix")),
            RecipeEntity(id = 3, title = "Omelette", categoryId = 1, imageUrl = "omelette.jpg", ingredients = listOf("eggs", "cheese"), method = listOf("whisk", "cook"))
        )
        recipeDao.insertAllRecipes(recipes)
        val result = recipeDao.getRecipesByCategory(1).first()
        assert(result.size == 2)
        assert(result.all { it.categoryId == 1 })
    }

    @Test
    fun emptyDatabaseReturnsEmptyList() = runTest {
        val result = categoryDao.getAllCategories().first()
        assert(result.isEmpty())
    }
}