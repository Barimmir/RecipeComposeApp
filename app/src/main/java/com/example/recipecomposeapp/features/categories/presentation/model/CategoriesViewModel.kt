package com.example.recipecomposeapp.features.categories.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.repository.RecipesRepository
import com.example.recipecomposeapp.data.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val repository: RecipesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(isLoading = true)
            }
        }
        viewModelScope.launch {
            repository.getCategories().collect { categoriesDto ->
                val categoriesList = categoriesDto.map { dto ->
                    dto.toUiModel()
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        categories = categoriesList
                    )
                }
            }
        }
    }
}