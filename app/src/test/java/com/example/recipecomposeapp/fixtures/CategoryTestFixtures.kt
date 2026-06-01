package com.example.recipecomposeapp.fixtures

import com.example.recipecomposeapp.data.model.CategoryDto

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