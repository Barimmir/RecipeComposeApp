package com.example.recipecomposeapp.features.core.network.api

import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.RecipeDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipesApiService {
    @GET("category")
    suspend fun getCategories(): List<CategoryDto>
    @GET("category/{id}/recipes")
    suspend fun getRecipesByCategory(@Path("id") categoryId: Int): List<RecipeDto>

    @GET("recipe/{id}")
    suspend fun getRecipe(@Path("id") recipeId: Int): RecipeDto
}