package com.example.recipecomposeapp.ui.theme.recipes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipecomposeapp.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.ui.theme.recipes.model.IngredientUiModel
import com.example.recipecomposeapp.ui.theme.recipes.model.RecipeUiModel
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    recipe: RecipeUiModel,
) {
    var currentPortions by remember { mutableStateOf(1) }

    val scaledIngredients = remember(currentPortions) {
        recipe.ingredients.map { ingredient ->
            ingredient.copy(
                amount = ingredient.amount * currentPortions
            )
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.TWO_HUNDRED_DP),
        ) {
            AsyncImage(
                model = recipe.imageUrl,
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.img_placeholder),
                error = painterResource(R.drawable.img_error)
            )
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = Dimens.SIXTEEN_DP, bottom = Dimens.SIXTEEN_DP),
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                shape = RoundedCornerShape(Dimens.TWELVE_DP)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = Dimens.TWENTY_EIGHT_SP
                )
            }
        }
    }
}

@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit
) {
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = 1f..12f,
        steps = 10
    )
}

@Composable
fun IngredientItem(
    ingredient: List<IngredientUiModel>,
    modifier: Modifier = Modifier,
) {

}

@Composable
fun InstructionsList(recipe: RecipeUiModel) {

}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    val sampleRecipe = RecipeUiModel(
        id = 0,
        title = "Классический бургер",
        imageUrl = "file:///android_asset/burger_hamburger.png",
        ingredients = emptyList(),
        method = "Приготовление...",
        isFavorite = false
    )
    RecipeComposeAppTheme {
        RecipeDetailsScreen(
            recipeId = 0,
            recipe = sampleRecipe,
        )
    }
}
