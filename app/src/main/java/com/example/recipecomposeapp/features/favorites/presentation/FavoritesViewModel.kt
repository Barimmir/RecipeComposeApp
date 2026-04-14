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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FavoritesViewModel(
    application: Application,
    private val recipesRepository: RecipesRepositoryStub = RecipesRepositoryStub
) : AndroidViewModel(application) {

    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        favoriteDataStoreManager.getFavoriteIdsFlow()
            .onEach { favoriteIds ->
                loadFavoriteRecipes(favoriteIds)
            }
            .launchIn(viewModelScope)
    }

    private fun loadFavoriteRecipes(favoriteIds: Set<String>) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val recipes = favoriteIds.mapNotNull { id ->
                    val intId = id.toIntOrNull()
                    intId?.let { recipesRepository.getRecipeById(it) }
                }.map { it.toUiModel() }

                _uiState.value = _uiState.value.copy(
                    favoriteRecipes = recipes,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки избранных рецептов: ${e.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
