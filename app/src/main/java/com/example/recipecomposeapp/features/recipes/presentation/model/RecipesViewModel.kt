package com.example.recipecomposeapp.features.recipes.presentation.model

import android.util.Log
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.repository.RecipesRepository
import com.example.recipecomposeapp.data.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder

class RecipesViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipesRepository,
) : ViewModel() {

    private val categoryId: Int = savedStateHandle["categoryId"] ?: 0
    private val rawCategoryTitle: String = savedStateHandle["categoryTitle"] ?: ""
    private val rawCategoryImageUrl: String = savedStateHandle["categoryImageUrl"] ?: ""
    private val categoryTitle: String = run {
        try {
            URLDecoder.decode(rawCategoryTitle, "UTF-8")
        } catch (e: Exception) {
            Uri.decode(rawCategoryTitle) ?: rawCategoryTitle
        }
    }

    private val categoryImageUrl: String = run {
        try {
            URLDecoder.decode(rawCategoryImageUrl, "UTF-8")
        } catch (e: Exception) {
            Uri.decode(rawCategoryImageUrl) ?: rawCategoryImageUrl
        }
    }

    private val _uiState = MutableStateFlow(
        RecipesUiState(
            categoryTitle = categoryTitle,
            categoryImageUrl = categoryImageUrl
        )
    )
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getRecipesByCategory(categoryId)
                .catch { e ->
                    Log.e("RecipesViewModel", "Error loading recipes", e)
                    _uiState.update { it.copy(isLoading = false) }
                }
                .collect { recipesDto ->
                val recipesList = recipesDto.map { dto ->
                    dto.toUiModel().copy(isFavorite = false)
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recipes = recipesList,
                        error = null
                    )
                }
            }
        }
    }

    fun refresh() {
        loadRecipes()
    }
}
