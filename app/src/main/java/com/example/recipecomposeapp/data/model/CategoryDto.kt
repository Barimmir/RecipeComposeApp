package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.features.core.utils.Constants
import com.example.recipecomposeapp.features.categories.presentation.model.CategoryUiModel

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