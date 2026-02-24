package com.example.recipecomposeapp.ui.theme.recipes.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryStub
import com.example.recipecomposeapp.data.model.toUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {
    private val repository = RecipesRepositoryStub
    private val _recipes = MutableStateFlow<List<RecipeUiModel>>(emptyList())
    val recipes: StateFlow<List<RecipeUiModel>> = _recipes

    fun loadRecipes(categoryId: Int) {
        viewModelScope.launch {
            val recipesDto = repository.getRecipesByCategoryId(categoryId)
            _recipes.value = recipesDto.map { it.toUiModel() }
        }
    }
}