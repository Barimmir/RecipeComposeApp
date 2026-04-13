package com.example.recipecomposeapp.features.navigation

import com.example.recipecomposeapp.features.core.utils.createRecipesRoute

sealed class Screen(val route: String) {
    object Categories : Screen("categories")
    object Favorites : Screen("favorites")

    object Recipes :
        Screen("recipes/{categoryId}?categoryTitle={categoryTitle}&categoryImageUrl={categoryImageUrl}") {
        fun createRoute(categoryId: Int, categoryTitle: String, categoryImageUrl: String): String {
            return createRecipesRoute(categoryId, categoryTitle, categoryImageUrl)
        }
    }

    sealed class RecipeDetails(route: String) : Screen(route) {
        object Base : RecipeDetails("recipe/{recipeId}") {
            fun createRoute(recipeId: Int) = "recipe/$recipeId"
        }
    }
}

const val KEY_RECIPE_OBJECT = "recipe_object"