package com.example.recipecomposeapp.features.core.utils

import android.content.Context
import android.content.Intent

fun shareRecipe(context: Context, recipeId: Int, recipeTitle: String) {
    val deepLink = createRecipeDeepLink(recipeId)
    val shareText = "Посмотри этот рецепт: $recipeTitle\n\n$deepLink"
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
        putExtra(Intent.EXTRA_SUBJECT, "Рецепт: $recipeTitle")
    }
    context.startActivity(Intent.createChooser(shareIntent, "Поделиться рецептом"))
}