package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.features.core.utils.Constants
import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeDtoMapperTest {

    @Test
    fun maps_DTO_to_UI_model_correctly() {
        val dto = RecipeDto(
            id = 42,
            title = "Test Recipe",
            ingredients = listOf(
                IngredientDto("200", "g", "Flour"),
                IngredientDto("100", "ml", "Milk")
            ),
            method = listOf("Step 1", "Step 2", "Step 3"),
            imageUrl = "cake.jpg"
        )

        val result = dto.toUiModel()

        assertEquals(42, result.id)
        assertEquals("Test Recipe", result.title)
        assertEquals(2, result.ingredients.size)
        assertEquals("Flour", result.ingredients[0].name)
        assertEquals("200", result.ingredients[0].amount)
        assertEquals("g", result.ingredients[0].unitOfMeasure)
        assertEquals(3, result.method.size)
        assertEquals("https://recipes.androidsprint.ru/api/images/cake.jpg", result.imageUrl)
        assertEquals(false, result.isFavorite)
    }

    @Test
    fun prepends_base_url_to_relative_imageUrl() {
        val dto = RecipeDto(
            id = 1,
            title = "Recipe",
            ingredients = emptyList(),
            method = emptyList(),
            imageUrl = "relative/path/image.png"
        )

        val result = dto.toUiModel()

        val expectedUrl = Constants.IMAGES_BASE_URL + "relative/path/image.png"
        assertEquals(expectedUrl, result.imageUrl)
    }

    @Test
    fun preserves_full_imageUrl_starting_with_http() {
        val dto = RecipeDto(
            id = 1,
            title = "Recipe",
            ingredients = emptyList(),
            method = emptyList(),
            imageUrl = "https://example.com/image.jpg"
        )

        val result = dto.toUiModel()

        assertEquals("https://example.com/image.jpg", result.imageUrl)
    }
}