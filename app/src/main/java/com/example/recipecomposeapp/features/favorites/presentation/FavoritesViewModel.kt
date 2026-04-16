package com.example.recipecomposeapp.features.favorites.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.data.model.toUiModel
import com.example.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class FavoritesViewModel(
    application: Application,
    private val recipesRepository: RecipesRepositoryStub = RecipesRepositoryStub
) : AndroidViewModel(application) {

    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        favoriteDataStoreManager.getFavoriteIdsFlow()
            .map { favoriteIds ->
                favoriteIds.mapNotNull { id -> 
                    id.toIntOrNull()?.let { recipesRepository.getRecipeById(it)?.toUiModel() }
                }
            }
            .onStart { 
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            }
            .onEach { recipes ->
                _uiState.value = _uiState.value.copy(favoriteRecipes = recipes, isLoading = false)
            }
            .catch { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки избранных рецептов: ${e.message}"
                )
            }
            .launchIn(viewModelScope)
    }


    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
