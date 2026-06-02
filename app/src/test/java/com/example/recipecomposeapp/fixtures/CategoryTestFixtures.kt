package com.example.recipecomposeapp.fixtures

import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.data.model.CategoryDto

object CategoryTestFixtures {

    fun createCategoryDto(
        id: Int = 1,
        title: String = "Тестовая категория",
        description: String = "Описание",
        imageUrl: String = "cat.png"
    ) = CategoryDto(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl
    )

    fun createCategoryDtoList() = listOf(
        createCategoryDto(id = 1, title = "Категория 1"),
        createCategoryDto(id = 2, title = "Категория 2")
    )

    fun createCategoryEntity(
        id: Int = 1,
        name: String = "Category 1",
        description: String = "Description 1",
        imageUrl: String = "http://example.com/cat1.jpg"
    ) = CategoryEntity(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl
    )

    fun createCategoryEntityList() = listOf(
        createCategoryEntity(id = 1, name = "Category 1", imageUrl = "http://example.com/cat1.jpg"),
        createCategoryEntity(id = 2, name = "Category 2", imageUrl = "http://example.com/cat2.jpg")
    )
}
