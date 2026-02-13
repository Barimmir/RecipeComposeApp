package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.Constants
import com.example.recipecomposeapp.ui.theme.categories.model.CategoryUiModel

data class CategoryDto(
    val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)

fun CategoryDto.toUiModel() = CategoryUiModel(
    id = id,
    title = title,
    description = description,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl
)