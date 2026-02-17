package com.example.recipecomposeapp.ui.theme.categories.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.data.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {
    private val repository = RecipesRepositoryStub
    private val _categories = MutableStateFlow<List<CategoryUiModel>>(emptyList())
    val categories: StateFlow<List<CategoryUiModel>> = _categories

    init {
        loadCategories()
    }
    private fun loadCategories() {
        viewModelScope.launch {
            val categoriesDto = repository.getCategories()
            val categoriesList = categoriesDto.map { dto ->
                dto.toUiModel()
            }
            _categories.value = categoriesList
        }

    }
}