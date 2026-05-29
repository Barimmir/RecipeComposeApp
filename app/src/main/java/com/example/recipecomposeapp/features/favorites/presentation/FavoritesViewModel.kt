package com.example.recipecomposeapp.features.favorites.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.FavoriteDataStoreManager
import com.example.recipecomposeapp.data.model.repository.RecipesRepository
import com.example.recipecomposeapp.data.model.toUiModel
import com.example.recipecomposeapp.features.favorites.presentation.model.FavoritesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FavoritesViewModel @Inject constructor(
    recipesRepository: RecipesRepository,
    favoriteDataStoreManager: FavoriteDataStoreManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    val favoriteCount: StateFlow<Int> = favoriteDataStoreManager.getFavoriteCountFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    init {
        favoriteDataStoreManager.getFavoriteIdsFlow()
            .flatMapLatest { favoriteIds ->
                val ids = favoriteIds.mapNotNull { it.toIntOrNull() }
                if (ids.isNotEmpty()) {
                    recipesRepository.getRecipesByIds(ids)
                } else {
                    flowOf(emptyList())
                }
            }
            .map { recipes -> recipes.map { it.toUiModel() } }
            .onStart {
                _uiState.update { it.copy(isLoading = true, error = null) }
            }
            .onEach { recipes ->
                _uiState.update { it.copy(favoriteRecipes = recipes, isLoading = false) }
            }
            .catch { e ->
                Log.e("FavoritesViewModel", "Ошибка загрузки избранных", e)
                _uiState.update { it.copy(isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

}
