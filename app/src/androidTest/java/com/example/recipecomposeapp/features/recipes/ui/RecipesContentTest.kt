package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel

@RunWith(AndroidJUnit4::class)
class RecipesContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showsLoadingState() {
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(isLoading = true),
                onRecipeClick = { _, _ -> }
            )
        }
        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun showsErrorState() {
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(error = "Network error"),
                onRecipeClick = { _, _ -> }
            )
        }
        composeTestRule.onNodeWithText("Network error").assertIsDisplayed()
    }

    @Test
    fun showsEmptyState() {
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(),
                onRecipeClick = { _, _ -> }
            )
        }
        composeTestRule.onNodeWithTag("empty_state").assertIsDisplayed()
    }

    @Test
    fun displaysRecipeList() {
        val recipe = RecipesUiModel(
            id = 1,
            title = "Бургер",
            imageUrl = "",
            ingredients = emptyList(),
            method = emptyList(),
            isFavorite = false
        )
        composeTestRule.setContent {
            RecipesContent(
                uiState = RecipesUiState(recipes = listOf(recipe)),
                onRecipeClick = { _, _ -> }
            )
        }
        composeTestRule.onNodeWithText("БУРГЕР").assertIsDisplayed()
    }
}
