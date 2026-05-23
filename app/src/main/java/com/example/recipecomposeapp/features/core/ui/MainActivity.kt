package com.example.recipecomposeapp.features.core.ui

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.recipecomposeapp.data.model.FavoritePrefsManager
import com.example.recipecomposeapp.data.network.api.RecipesApiService
import com.example.recipecomposeapp.features.core.utils.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private lateinit var apiService: RecipesApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val jsonConverter = Json.asConverterFactory("application/json".toMediaType())
        val logging = HttpLoggingInterceptor().apply {
            level = if (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(jsonConverter)
            .client(client)
            .build()
        apiService = retrofit.create(RecipesApiService::class.java)
        FavoritePrefsManager.init(this)
        intent?.data?.let {
            deepLinkIntent = intent
        }
        enableEdgeToEdge()
        setContent {
            RecipesApp(
                deepLinkIntent = deepLinkIntent,
                apiService = apiService
            )
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