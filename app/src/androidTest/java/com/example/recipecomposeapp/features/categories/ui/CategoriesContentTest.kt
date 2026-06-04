package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesUiState
import com.example.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CategoriesContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysCategories() {
        val category = CategoryUiModel(
            id = 1,
            title = "Бургеры",
            description = "Рецепты бургеров",
            imageUrl = ""
        )
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(categories = listOf(category)),
                onCategoryClick = {}
            )
        }
        composeTestRule.onNodeWithText("БУРГЕРЫ").assertIsDisplayed()
    }

    @Test
    fun clickingCategoryNavigatesToRecipes() {
        var clickedId = -1
        val category = CategoryUiModel(
            id = 42,
            title = "Пицца",
            description = "Описание",
            imageUrl = ""
        )
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(categories = listOf(category)),
                onCategoryClick = { clickedId = it }
            )
        }
        composeTestRule.onNodeWithText("ПИЦЦА").performClick()
        assertEquals(42, clickedId)
    }

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            CategoriesContent(
                uiState = CategoriesUiState(isLoading = true),
                onCategoryClick = {}
            )
        }
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }
}
