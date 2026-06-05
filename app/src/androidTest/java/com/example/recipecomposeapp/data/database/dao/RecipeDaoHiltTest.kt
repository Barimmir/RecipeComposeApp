package com.example.recipecomposeapp.data.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RecipeDaoHiltTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: RecipesDatabase

    private lateinit var categoryDao: CategoryDao

    @Before
    fun setUp() {
        hiltRule.inject()
        categoryDao = database.categoryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun hiltInjectsRealDatabase() = runTest {
        val categories = listOf(
            CategoryEntity(1, "Desserts", "Sweet treats", "desserts.jpg")
        )
        categoryDao.insertAllCategories(categories)
        val result = categoryDao.getAllCategories().first()
        assertEquals(1, result.size)
        assertEquals("Desserts", result[0].name)
    }
}