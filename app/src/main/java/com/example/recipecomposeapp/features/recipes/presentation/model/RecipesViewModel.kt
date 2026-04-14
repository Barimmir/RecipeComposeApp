package com.example.recipecomposeapp.features.recipes.presentation.model

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.data.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.URLDecoder

class RecipesViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {
    private val repository = RecipesRepositoryStub
    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)

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
        setupFavoriteSubscription()
    }

    private fun setupFavoriteSubscription() {
        favoriteDataStoreManager.getFavoriteIdsFlow()
            .onEach { favoriteIds ->
                updateRecipesFavoriteStatus(favoriteIds)
            }
            .launchIn(viewModelScope)
    }

    private fun updateRecipesFavoriteStatus(favoriteIds: Set<String>) {
        _uiState.update { currentState ->
            currentState.copy(
                recipes = currentState.recipes.map { recipe ->
                    recipe.copy(
                        isFavorite = favoriteIds.contains(recipe.id.toString())
                    )
                }
            )
        }
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val recipesDto = repository.getRecipesByCategoryId(categoryId)
                val favoriteIds = favoriteDataStoreManager.getFavoriteIdsFlow().first()
                
                val recipesList = recipesDto.map { dto ->
                    dto.toUiModel().copy(
                        isFavorite = favoriteIds.contains(dto.id.toString())
                    )
                }

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        recipes = recipesList,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Error loading recipes"
                    )
                }
                e.printStackTrace()
            }
        }
    }

    fun refresh() {
        loadRecipes()
    }
        
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
