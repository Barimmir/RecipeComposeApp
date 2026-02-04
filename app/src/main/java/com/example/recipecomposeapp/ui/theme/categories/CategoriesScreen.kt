package com.example.recipecomposeapp.ui.theme.categories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.ui.theme.categories.components.ScreenHeader

@Composable
fun CategoriesScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ScreenHeader(
            "Категории",
            onClick = {},
        )

    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    CategoriesScreen()
}
