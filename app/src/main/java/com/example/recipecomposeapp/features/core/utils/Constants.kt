package com.example.recipecomposeapp.features.core.utils

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val DEEP_LINK_SCHEME = "recipeapp"
    const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"
}

fun createRecipeDeepLink(recipeId: Int): String {
    return "${Constants.DEEP_LINK_BASE_URL}/recipe/$recipeId"
}