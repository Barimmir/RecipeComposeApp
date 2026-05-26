package com.example.recipecomposeapp.features.favorites.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.model.repository.RecipesRepository
import com.example.recipecomposeapp.data.model.toUiModel
import com.example.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModel(
    private val recipesRepository: RecipesRepository,
    private val favoriteDataStoreManager: FavoriteDataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        favoriteDataStoreManager.getFavoriteIdsFlow()
            .mapLatest { favoriteIds ->
                val ids = favoriteIds.mapNotNull { it.toIntOrNull() }
                if (ids.isNotEmpty()) {
                    recipesRepository.getRecipesByIds(ids).first().map { it.toUiModel() }
                } else {
                    emptyList()
                }
            }
            .onStart {
                _uiState.update { it.copy(isLoading = true, error = null) }
            }
            .onEach { recipes ->
                _uiState.update { it.copy(favoriteRecipes = recipes, isLoading = false) }
            }
            .catch { e ->
                Log.e("FavoritesViewModel", "Error loading favorites", e)
                _uiState.update { it.copy(isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

}
