package com.example.recipecomposeapp.app.di

import com.example.recipecomposeapp.data.model.repository.RecipesRepository
import com.example.recipecomposeapp.features.categories.presentation.model.CategoriesViewModel

class CategoriesViewModelFactory(
    private val repository: RecipesRepository
) : Factory<CategoriesViewModel> {
    override fun create(): CategoriesViewModel {
        return CategoriesViewModel(repository)
    }
}
