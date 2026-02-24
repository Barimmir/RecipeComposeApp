package com.example.recipecomposeapp.ui.theme.navigation

sealed class Screen(val route: String) {
    object Categories : Screen("categories")
    object Favorites : Screen("favorites")
    object Recipes : Screen("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/$categoryId"
    }
}