package com.example.recipecomposeapp.ui.theme.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .navigationBarsPadding()) {
        Button(onClick = onCategoriesClick) {
            Text("Категории")
        }
        Button(onClick = onFavoriteClick) {
            Text("Избранные")
        }
    }
}



