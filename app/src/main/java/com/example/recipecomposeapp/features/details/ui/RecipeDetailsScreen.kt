package com.example.recipecomposeapp.features.details.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.recipecomposeapp.features.core.utils.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.features.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.features.core.ui.ScreenHeader
import com.example.recipecomposeapp.features.details.presentation.RecipeDetailsViewModel
import com.example.recipecomposeapp.features.recipes.presentation.model.IngredientsUiModel
import com.example.recipecomposeapp.features.recipes.presentation.model.RecipesUiModel
import kotlin.math.roundToInt

@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewModel,
    recipeId: Int,
    shareRecipe: (Context, Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }
    uiState.recipe?.let { recipe ->
        RecipeDetailsContent(
            recipe = recipe,
            numberOfServings = uiState.numberOfServings,
            scaledIngredients = uiState.scaledIngredients,
            isLoading = uiState.isLoading,
            error = uiState.error,
            onPortionsChange = viewModel::updatePortions,
            onFavoriteClick = viewModel::toggleFavorite,
            onShareClick = { shareRecipe(context, recipe.id, recipe.title) },
            onErrorDismiss = viewModel::clearError,
            modifier = modifier
        )
    }

    if (uiState.isLoading && uiState.recipe == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (uiState.recipe == null && !uiState.isLoading && uiState.hasError) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(Dimens.SIXTEEN_DP),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = uiState.error ?: "Рецепт не найден",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun RecipeDetailsContent(
    recipe: RecipesUiModel,
    numberOfServings: Int,
    scaledIngredients: List<IngredientsUiModel>,
    isLoading: Boolean,
    error: String?,
    onPortionsChange: (Int) -> Unit,
    onFavoriteClick: () -> Unit,
    onShareClick: () -> Unit,
    onErrorDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val recipePainter = rememberAsyncImagePainter(recipe.imageUrl)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(Dimens.EIGHT_DP),
    ) {
        ScreenHeader(
            title = recipe.title,
            imagePainter = recipePainter,
            contentDescription = "Заголовок рецептов",
            showShareButton = true,
            onShareClick = onShareClick,
            isFavorite = recipe.isFavorite,
            showFavoriteButton = true,
            onFavoriteClick = onFavoriteClick
        )

        Text(
            text = "Ингредиенты".uppercase(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = Dimens.SIXTEEN_DP)
        )

        Text(
            text = pluralStringResource(
                R.plurals.portions_count,
                numberOfServings,
                numberOfServings
            ),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = Dimens.SIXTEEN_DP)
        )

        PortionsSlider(
            currentPortions = numberOfServings,
            onPortionsChange = onPortionsChange
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
                        IngredientItem(
                            index = index,
                            ingredient = ingredient,
                            scaledIngredients = scaledIngredients
                        )
                    }
                }
            }
        }

        Text(
            text = "Способ приготовления".uppercase(),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = Dimens.SIXTEEN_DP)
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
                        text = "${index + 1}.$step",
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

@Composable
fun IngredientItem(
    index: Int,
    ingredient: IngredientsUiModel,
    scaledIngredients: List<IngredientsUiModel>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
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

fun formatAmount(amount: Float): String {
    return when {
        amount >= 1 -> amount.roundToInt().toString()
        amount in 0.75..<1.0 -> "3/4"
        amount in 0.5..<0.75 -> "1/2"
        amount in 0.25..<0.5 -> "1/4"
        amount > 0 && amount < 0.25 -> "щепотка"
        else -> "по вкусу"
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    val sampleIngredients = listOf(
        IngredientsUiModel(
            name = "Говяжья котлета",
            amount = 1.0f,
            unitOfMeasure = "шт"
        ),
        IngredientsUiModel(
            name = "Булочка",
            amount = 1.0f,
            unitOfMeasure = "шт"
        ),
        IngredientsUiModel(
            name = "Сыр",
            amount = 50.0f,
            unitOfMeasure = "г"
        )
    )
    val sampleRecipe = RecipesUiModel(
        id = 0,
        title = "Классический бургер",
        imageUrl = "file:///android_asset/burger_hamburger.png",
        ingredients = sampleIngredients,
        method = "\n В глубокой миске смешайте говяжий фарш, лук, чеснок, соль и перец. Разделите фарш на 4 равные части и сформируйте котлеты." +
                "\n Разогрейте сковороду на среднем огне. Обжаривайте котлеты с каждой стороны в течение 4-5 минут или до желаемой степени прожарки." +
                "\n В то время как котлеты готовятся, подготовьте булочки. Разрежьте их пополам и обжарьте на сковороде до золотистой корочки." +
                "\n Смазать нижние половинки булочек горчицей и кетчупом, затем положите лист салата, котлету, кольца помидора и закройте верхней половинкой булочки." +
                "\n Подавайте бургеры горячими с картофельными чипсами или картофельным пюре.",
        isFavorite = false
    )
    RecipeComposeAppTheme {
        RecipeDetailsContent(
            recipe = sampleRecipe,
            numberOfServings = 1,
            scaledIngredients = sampleIngredients,
            isLoading = false,
            error = null,
            onPortionsChange = {},
            onFavoriteClick = {},
            onShareClick = {},
            onErrorDismiss = {}
        )
    }
}
