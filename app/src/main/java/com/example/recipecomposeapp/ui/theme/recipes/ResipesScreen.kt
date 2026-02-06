package com.example.recipecomposeapp.ui.theme.recipes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.ScreenHeader

@Composable
fun RecipesScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        ScreenHeader(
            "Скоро здесь будет список рецептов",
            onClick = {},
            imagePainter = painterResource(id = R.drawable.bcg_categories)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    RecipesScreen()
}