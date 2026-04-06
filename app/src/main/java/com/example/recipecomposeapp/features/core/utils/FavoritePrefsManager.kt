package com.example.recipecomposeapp.features.core.utils

import android.content.Context
import androidx.core.content.edit

object FavoritePrefsManager {
    private var appContext: Context? = null
    private val sharedPreferences
        get() =
            appContext?.getSharedPreferences("recipe_app_prefs", Context.MODE_PRIVATE)

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun getCurrentFavorites(): Set<String> {
        return sharedPreferences?.getStringSet("favorite_recipe_ids", emptySet()) ?: emptySet()
    }

    private fun getUpdateFavorites(): MutableSet<String> {
        return getCurrentFavorites().toMutableSet()
    }

    fun isFavorite(recipeId: Int): Boolean {
        return recipeId.toString() in getUpdateFavorites()
    }

    fun addToFavorites(recipeId: Int) {
        val updatedFavorites = getUpdateFavorites()
        updatedFavorites.add(recipeId.toString())
        sharedPreferences?.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        val updatedFavorites = getUpdateFavorites()
        updatedFavorites.remove(recipeId.toString())
        sharedPreferences?.edit {
            putStringSet("favorite_recipe_ids", updatedFavorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        return getUpdateFavorites()
    }
}