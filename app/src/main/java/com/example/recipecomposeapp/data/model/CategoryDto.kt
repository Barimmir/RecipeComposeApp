package com.example.recipecomposeapp.data.model

import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.features.core.utils.Constants
import com.example.recipecomposeapp.features.categories.presentation.model.CategoryUiModel
import kotlinx.serialization.Serializable

@Serializable
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
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.IMAGES_BASE_URL + imageUrl
)

fun CategoryDto.toEntity() = CategoryEntity(
    id = id,
    name = title,
    description = description,
    imageUrl = imageUrl
)

fun CategoryEntity.toDto() = CategoryDto(
    id = id,
    title = name,
    description = description,
    imageUrl = imageUrl
)