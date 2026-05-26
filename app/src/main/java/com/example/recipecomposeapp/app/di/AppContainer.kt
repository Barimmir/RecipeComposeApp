package com.example.recipecomposeapp.app.di

import android.content.Context
import android.content.pm.ApplicationInfo
import com.example.recipecomposeapp.data.database.RecipesDatabase
import com.example.recipecomposeapp.data.model.repository.RecipesRepository
import com.example.recipecomposeapp.data.model.repository.RecipesRepositoryImpl
import com.example.recipecomposeapp.data.network.api.RecipesApiService
import com.example.recipecomposeapp.features.core.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class AppContainer(context: Context) {

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = if (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
        )
        .build()

    val json: Json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient)
        .build()

    val recipesApi: RecipesApiService = retrofit.create(RecipesApiService::class.java)

    val recipesDatabase: RecipesDatabase = RecipesDatabase.buildDatabase(context)

    val recipesRepository: RecipesRepository = RecipesRepositoryImpl(
        apiService = recipesApi,
        database = recipesDatabase
    )
}
