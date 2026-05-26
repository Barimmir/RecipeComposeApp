package com.example.recipecomposeapp.app.di

import android.app.Application
import com.example.recipecomposeapp.data.model.FavoriteDataStoreManager
import com.example.recipecomposeapp.features.favorites.presentation.FavoritesViewModel

class FavoritesViewModelFactory(
    private val application: Application
) : Factory<FavoritesViewModel> {
    override fun create(): FavoritesViewModel {
        val appContainer = (application as RecipeApplication).appContainer
        return FavoritesViewModel(
            recipesRepository = appContainer.recipesRepository,
            favoriteDataStoreManager = FavoriteDataStoreManager(application)
        )
    }
}
