package com.example.recipecomposeapp.features.details.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.model.repository.RecipesRepository
import com.example.recipecomposeapp.data.model.toUiModel
import com.example.recipecomposeapp.features.details.presentation.model.RecipeDetailsUiState
import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientsUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    application: Application,
    private val repository: RecipesRepository
) : AndroidViewModel(application) {

    private val favoriteDataStoreManager = FavoriteDataStoreManager(application)

    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    private val recipeId: Int = savedStateHandle.get<Int>("recipeId")
        ?: throw IllegalArgumentException("recipeId необходим")

    init {
        setupFavoriteSubscription()
        viewModelScope.launch {
            repository.getRecipe(recipeId).collect { recipeDto ->
                if (recipeDto != null) {
                    val favoriteIds = favoriteDataStoreManager.getFavoriteIdsFlow().first()
                    val recipe = recipeDto.toUiModel().copy(
                        isFavorite = favoriteIds.contains(recipeId.toString())
                    )
                    _uiState.value = _uiState.value.copy(
                        recipe = recipe,
                        currentPortions = 1,
                        scaledIngredients = recipe.ingredients,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun setupFavoriteSubscription() {
        favoriteDataStoreManager.getFavoriteIdsFlow()
            .onEach { favoriteIds ->
                val currentRecipe = _uiState.value.recipe
                if (currentRecipe != null) {
                    val newFavoriteStatus = favoriteIds.contains(recipeId.toString())
                    if (currentRecipe.isFavorite != newFavoriteStatus) {
                        _uiState.value = _uiState.value.copy(
                            recipe = currentRecipe.copy(isFavorite = newFavoriteStatus)
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun toggleFavorite() {
        val recipe = _uiState.value.recipe ?: return
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

    fun updatePortions(newPortions: Int) {
        val recipe = _uiState.value.recipe ?: return
        val currentPortions = _uiState.value.currentPortions
        val scaledIngredients =
            calculateScaledIngredients(recipe.ingredients, currentPortions, newPortions)

        _uiState.value = _uiState.value.copy(
            currentPortions = newPortions,
            scaledIngredients = scaledIngredients
        )
    }

    private fun calculateScaledIngredients(
        ingredients: List<IngredientsUiModel>,
        originalServings: Int,
        newServings: Int
    ): List<IngredientsUiModel> {
        if (originalServings == newServings) return ingredients

        val scaleFactor = newServings.toFloat() / originalServings

        return ingredients.map { ingredient ->
            val newAmount = try {
                ingredient.amount.toFloat() * scaleFactor
            } catch (e: NumberFormatException) {
                ingredient.amount.toFloat()
            }
            ingredient.copy(
                amount = newAmount.toString()
            )
        }
    }


    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
