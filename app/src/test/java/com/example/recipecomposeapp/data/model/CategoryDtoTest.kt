package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.features.core.utils.Constants
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryDtoTest {

    @Test
    fun `converts DTO to UI model`() {
        val dto = CategoryDto(
            id = 1,
            title = "Test Category",
            description = "Test Description",
            imageUrl = "test_image.jpg"
        )

        val uiModel = dto.toUiModel()

        assertEquals(dto.id, uiModel.id)
        assertEquals(dto.title, uiModel.title)
        assertEquals(dto.description, uiModel.description)
        assertEquals(Constants.IMAGES_BASE_URL + dto.imageUrl, uiModel.imageUrl)
    }

    @Test
    fun `converts DTO with full URL to UI model`() {
        val fullUrl = "https://example.com/image.jpg"
        val dto = CategoryDto(
            id = 2,
            title = "Full URL Category",
            description = "Description with full URL",
            imageUrl = fullUrl
        )

        val uiModel = dto.toUiModel()

        assertEquals(fullUrl, uiModel.imageUrl)
    }
}
