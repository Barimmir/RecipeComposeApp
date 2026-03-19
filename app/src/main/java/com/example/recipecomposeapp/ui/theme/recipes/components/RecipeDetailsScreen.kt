package com.example.recipecomposeapp.ui.theme.recipes.components

import android.view.Surface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipecomposeapp.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.ui.theme.recipes.model.IngredientUiModel
import com.example.recipecomposeapp.ui.theme.recipes.model.RecipeUiModel
import java.nio.file.WatchEvent
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    recipe: RecipeUiModel,
) {
    val scrollState = rememberScrollState()
    var currentPortions by remember { mutableIntStateOf(1) }

    val scaledIngredients = remember(currentPortions, recipe.ingredients) {
        recipe.ingredients.map { ingredient ->
            ingredient.copy(
                amount = ingredient.amount * currentPortions
            )

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP),
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
        Text(
            text = "Ингредиенты".uppercase(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                horizontal = Dimens.SIXTEEN_DP
            )
        )
        Text(
            text = pluralStringResource(
                R.plurals.portions_count,
                currentPortions,
                currentPortions
            ),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = Dimens.SIXTEEN_DP)
        )
        PortionsSlider(
            currentPortions = currentPortions,
            onPortionsChange = { newPortion ->
                currentPortions = newPortion
            }
        )
        if (scaledIngredients.isNotEmpty()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.SIXTEEN_DP),
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Dimens.TWELVE_DP)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.EIGHT_DP)
                ) {
                    scaledIngredients.forEachIndexed { index, ingredient ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = Dimens.SIXTEEN_DP,
                                    vertical = Dimens.EIGHT_DP
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = ingredient.name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${formatAmount(ingredient.amount)} ${ingredient.unitOfMeasure}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        if (index < scaledIngredients.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = Dimens.SIXTEEN_DP)
                            )
                        }
                    }
                }
            }
        }
        Text(
            text = "Способ приготовления".uppercase(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(
                horizontal = Dimens.SIXTEEN_DP
            )
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.SIXTEEN_DP)
                .padding(bottom = Dimens.SIXTEEN_DP),
            color = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(Dimens.TWELVE_DP)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.EIGHT_DP)
            ) {
                val methodSteps = recipe.method.split("\n").filter { it.isNotBlank() }

                methodSteps.forEachIndexed { index, step ->
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = Dimens.SIXTEEN_DP, vertical = Dimens.EIGHT_DP)
                    )

                    if (index < methodSteps.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Dimens.SIXTEEN_DP)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PortionsSlider(
    currentPortions: Int,
    onPortionsChange: (Int) -> Unit,
) {
    Slider(
        value = currentPortions.toFloat(),
        onValueChange = { onPortionsChange(it.roundToInt()) },
        valueRange = 1f..12f,
        steps = 10
    )
}

fun formatAmount(amount: Float): String {
    return when {
        amount >= 0.75 -> "3/4"
        amount >= 0.5 -> "1/2"
        amount >= 0.25 -> "1/4"
        amount > 0 -> "щепотка"
        else -> "по вкусу"
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    val sampleIngredients = listOf(
        IngredientUiModel(
            name = "Говяжья котлета",
            amount = 1.0f,
            unitOfMeasure = "шт"
        ),
        IngredientUiModel(
            name = "Булочка",
            amount = 1.0f,
            unitOfMeasure = "шт"
        ),
        IngredientUiModel(
            name = "Сыр",
            amount = 50.0f,
            unitOfMeasure = "г"
        )
    )
    val sampleRecipe = RecipeUiModel(
        id = 0,
        title = "Классический бургер",
        imageUrl = "file:///android_asset/burger_hamburger.png",
        ingredients = sampleIngredients,
        method = "\n1. В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты." +
                "\n2. Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки." +
                "\n3. В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки." +
                "\n4. Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки." +
                "\n5. Подавайте бургеры горячими с картофельными чипсами или картофельным пюре.",
        isFavorite = false
    )
    RecipeComposeAppTheme {
        RecipeDetailsScreen(
            recipeId = 0,
            recipe = sampleRecipe,
        )
    }
}
