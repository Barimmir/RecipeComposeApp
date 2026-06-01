package com.example.recipecomposeapp.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryDtoTest {

    @Test
    fun mapper_maps_empty_title_correctly() {
        val dto = CategoryDto(
            id = 0,
            title = "",
            description = "Some description",
            imageUrl = "https://example.com/img.png"
        )

        val result = dto.toUiModel()

        assertEquals("", result.title)
        assertEquals("Some description", result.description)
    }

    @Test
    fun mapper_preserves_very_long_description() {
        val longDescription = "A".repeat(10000)
        val dto = CategoryDto(
            id = 1,
            title = "Category",
            description = longDescription,
            imageUrl = "img.jpg"
        )

        val result = dto.toUiModel()

        assertEquals(longDescription, result.description)
        assertEquals(10000, result.description.length)
    }
}