package com.example.recipecomposeapp.features.core.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.example.recipecomposeapp.data.model.FavoritePrefsManager
import com.example.recipecomposeapp.features.core.network.api.RecipesApiService
import com.example.recipecomposeapp.features.core.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val jsonConverter = Json.asConverterFactory("application/json".toMediaType())
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(jsonConverter)
        .build()
    private val apiService: RecipesApiService = retrofit.create(RecipesApiService::class.java)

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.i("!!!", "Метод onCreate() выполняется на потоке: ${Thread.currentThread().name}")
        super.onCreate(savedInstanceState)
        FavoritePrefsManager.init(this)
        intent?.data?.let {
            deepLinkIntent = intent
        }
        enableEdgeToEdge()
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                try {
                    Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                    val categories = apiService.getCategories()
                    Log.i("!!!", categories.toString())
                    categories.forEach { category ->
                        launch(Dispatchers.IO) {
                            try {
                                Log.i(
                                    "!!!",
                                    "Выполняю запрос рецептов для категории ${category.title}: ${Thread.currentThread().name}"
                                )
                                val recipesCall = apiService.getRecipesByCategory(category.id)
                                Log.i("!!!", "${recipesCall.size}")

                            } catch (e: Exception) {
                                Log.i("!!!", "${e.message}")
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.i("!!!", "${e.message}")
                }
            } catch (e: Exception) {
                Log.i("!!!", "${e.message}")
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let { _ ->
            deepLinkIntent = intent
        }
        setIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}