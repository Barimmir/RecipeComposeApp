package com.example.recipecomposeapp.util

import android.content.Context
import androidx.core.content.edit

object FavoritePrefsManager {
    private var appContext: Context? = null
    private val sharedPreferences =
        appContext?.getSharedPreferences("recipe_app_prefs", Context.MODE_PRIVATE)
    private val currentFavorites =
        sharedPreferences?.getStringSet("favorite_recipe_ids", emptySet())
    private val updatedFavorites = currentFavorites?.toMutableSet() ?: mutableSetOf()

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun isFavorite(recipeId: Int): Boolean {
        return recipeId.toString() in updatedFavorites
    }

    fun addToFavorites(recipeId: Int) {
        updatedFavorites.add(recipeId.toString())
        sharedPreferences?.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        updatedFavorites.remove(recipeId.toString())
        sharedPreferences?.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        return updatedFavorites
    }
}