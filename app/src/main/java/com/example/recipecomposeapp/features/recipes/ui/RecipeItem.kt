package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel

@Composable
fun RecipeItem(
    recipe: RecipesUiModel,
    onRecipeClick: (Int, RecipesUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable { onRecipeClick(recipe.id, recipe) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.img_placeholder),
                error = painterResource(R.drawable.img_error)
            )
            Text(
                text = recipe.title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(
                    horizontal = Dimens.SIXTEEN_DP,
                    vertical = Dimens.EIGHT_DP
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() {
    val sampleRecipe = RecipesUiModel(
        id = 0,
        title = "Classic Burger",
        imageUrl = "file:///android_asset/burger_hamburger.png",
        ingredients = emptyList(),
        method = "Cooking...",
        isFavorite = false
    )

    RecipeComposeAppTheme {
        RecipeItem(
            recipe = sampleRecipe,
            onRecipeClick = { _, _ -> },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
    }
}
