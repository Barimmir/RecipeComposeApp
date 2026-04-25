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
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
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


        threadPool.execute {
            try {
                var connection: HttpURLConnection? = null
                try {
                    Log.i("!!!", "Выполняю запрос на потоке: ${Thread.currentThread().name}")
                    val url = URL("https://recipes.androidsprint.ru/api/category")
                    connection = url.openConnection() as HttpURLConnection
                    connection.connect()
                    Log.i("!!!", "${connection.responseMessage}")
                    Log.i("!!!", "${connection.responseCode}")
                    val body = connection.getInputStream().bufferedReader().use { it.readText() }
                    Log.i("!!!", "Body: $body")

                    val json = Json { ignoreUnknownKeys = true }

                    val categories = json.decodeFromString<List<CategoryDto>>(body)
                    Log.i("!!!", "Получено категорий: ${categories.size}")
                    categories.forEach { category ->
                        threadPool.execute {
                            var recipeConnection: HttpURLConnection? = null
                            try {
                                Log.i(
                                    "!!!",
                                    "Выполняю запрос рецептов для категории ${category.title}: ${Thread.currentThread().name}"
                                )
                                val recipeUrl =
                                    URL("https://recipes.androidsprint.ru/api/category/${category.id}/recipes")
                                recipeConnection = recipeUrl.openConnection() as HttpURLConnection
                                recipeConnection.connect()
                                val recipeBody = recipeConnection.getInputStream().bufferedReader()
                                    .use { it.readText() }
                                Log.i(
                                    "!!!",
                                    "Получено рецептов для категории ${category.title}: ${recipeBody.length} "
                                )
                            } catch (e: Exception) {
                                Log.i("!!!", "${e.message}")
                            } finally {
                                recipeConnection?.disconnect()
                            }
                        }
                    }

                } catch (e: Exception) {
                    Log.i("!!!", "${e.message}")
                } finally {
                    connection?.disconnect()
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