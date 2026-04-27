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
import com.example.recipecomposeapp.data.model.CategoryDto
import com.example.recipecomposeapp.data.model.FavoritePrefsManager
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    private val okHttpClient = OkHttpClient()
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
        thread {
            try {
                try {
                    Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")

                    val request = Request
                        .Builder()
                        .url("https://recipes.androidsprint.ru/api/category")
                        .build()
                    okHttpClient.newCall(request).execute().use { response ->
                        val responseBody = response.body.string()
                        Log.i("!!!", response.message)
                        Log.i("!!!", "${response.code}")
                        Log.i("!!!", "Body: $responseBody")
                        val json = Json { ignoreUnknownKeys = true }
                        val categories =
                            json.decodeFromString<List<CategoryDto>>(responseBody)
                        Log.i("!!!", "Получено категорий: ${categories.size}")
                        categories.forEach { category ->
                            thread {
                                try {
                                    Log.i(
                                        "!!!",
                                        "Выполняю запрос рецептов для категории ${category.title}: ${Thread.currentThread().name}"
                                    )
                                    val requestRecipes = Request
                                        .Builder()
                                        .url("https://recipes.androidsprint.ru/api/category/${category.id}/recipes")
                                        .build()
                                    okHttpClient.newCall(requestRecipes).execute().use { response ->
                                        Log.i(
                                            "!!!",
                                            "Получено рецептов для категории ${category.title}: ${response.body.string().length} "
                                        )
                                    }
                                } catch (e: Exception) {
                                    Log.i("!!!", "${e.message}")
                                }
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
        threadPool.shutdown()
    }
}