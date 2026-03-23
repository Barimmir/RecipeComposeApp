package com.example.recipecomposeapp

import com.example.recipecomposeapp.Constants.DEEP_LINK_BASE_URL

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val DEEP_LINK_SCHEME = "recipeapp"
    const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"
}

fun createRecipeDeepLink(recipeId: Int): String {
    return "$DEEP_LINK_BASE_URL/recipe/$recipeId"
}