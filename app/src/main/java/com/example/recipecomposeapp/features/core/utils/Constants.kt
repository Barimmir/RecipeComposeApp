package com.example.recipecomposeapp.features.core.utils

import java.net.URLEncoder

object Constants {
    const val ASSETS_URI_PREFIX = "file:///android_asset/"
    const val DEEP_LINK_SCHEME = "recipeapp"
    const val DEEP_LINK_BASE_URL = "https://recipes.androidsprint.ru"
}

fun createRecipeDeepLink(recipeId: Int): String {
    return "${Constants.DEEP_LINK_BASE_URL}/recipe/$recipeId"
}

fun createRecipesRoute(categoryId: Int, categoryTitle: String, categoryImageUrl: String): String {
    val encodedTitle = URLEncoder.encode(categoryTitle, "UTF-8")
    val encodedImageUrl = URLEncoder.encode(categoryImageUrl, "UTF-8")
    return "recipes/$categoryId?categoryTitle=$encodedTitle&categoryImageUrl=$encodedImageUrl"
}