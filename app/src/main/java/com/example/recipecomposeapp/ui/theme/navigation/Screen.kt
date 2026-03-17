package com.example.recipecomposeapp.ui.theme.navigation

sealed class Screen(val route: String) {
    object Categories : Screen("categories")
    object Favorites : Screen("favorites")
    object Recipes : Screen("recipes/{categoryId}") {
        fun createRoute(categoryId: Int) = "recipes/$categoryId"
    }

    sealed class RecipeDetails(route: String) : Screen(route) {
        object Base : RecipeDetails("recipe/{recipeId}") {
            fun createRoute(recipeId: Int) = "recipe/$recipeId"
        }
    }
}

const val KEY_RECIPE_OBJECT = "recipe_object"