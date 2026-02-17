package com.example.recipecomposeapp.ui.theme.categories.model

import androidx.lifecycle.ViewModel
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub

class CategoriesViewModel : ViewModel() {
    private val repository = RecipesRepositoryStub
    val categories: List<CategoryUiModel> = loadCategories()
    private fun loadCategories(): List<CategoryUiModel> {
        val categoriesDto = repository.getCategories()
        return categoriesDto.map { dto ->
            CategoryUiModel(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                imageUrl = dto.imageUrl
            )
        }
    }


}