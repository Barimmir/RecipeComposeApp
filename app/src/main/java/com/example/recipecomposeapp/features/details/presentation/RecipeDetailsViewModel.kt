package com.example.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.data.model.toUiModel
import com.example.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    application: Application,
    private val recipesRepository: RecipesRepositoryStub = RecipesRepositoryStub
) : AndroidViewModel(application) {

    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    private var currentRecipeId: Int? = null

    init {
        setupFavoriteSubscription()
    }

    private fun setupFavoriteSubscription() {
        favoriteDataStoreManager.getFavoriteIdsFlow()
            .onEach { favoriteIds ->
                currentRecipeId?.let { recipeId ->
                    val currentRecipe = _uiState.value.recipe
                    if (currentRecipe != null && currentRecipe.id == recipeId) {
                        val newFavoriteStatus = favoriteIds.contains(recipeId.toString())
                        if (currentRecipe.isFavorite != newFavoriteStatus) {
                            _uiState.value = _uiState.value.copy(
                                recipe = currentRecipe.copy(isFavorite = newFavoriteStatus)
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadRecipe(recipeId: Int) {
        currentRecipeId = recipeId
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val recipeDto = recipesRepository.getRecipeById(recipeId)
                if (recipeDto != null) {
                    val favoriteIds = favoriteDataStoreManager.getFavoriteIdsFlow().first()
                    val recipe = recipeDto.toUiModel().copy(
                        isFavorite = favoriteIds.contains(recipeId.toString())
                    )
                    _uiState.value = _uiState.value.copy(
                        recipe = recipe,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Рецепт не найден"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Ошибка загрузки рецепта: ${e.message}"
                )
            }
        }
    }

    fun toggleFavorite() {
        val recipe = _uiState.value.recipe ?: return
        currentRecipeId?.let { recipeId ->
            viewModelScope.launch {
                try {
                    if (recipe.isFavorite) {
                        favoriteDataStoreManager.removeFavorite(recipeId)
                    } else {
                        favoriteDataStoreManager.addFavorite(recipeId)
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        error = "Ошибка при изменении избранного: ${e.message}"
                    )
                }
            }
        }
    }

    fun updatePortions(newPortions: Int) {
        _uiState.value = _uiState.value.copy(numberOfServings = newPortions)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
