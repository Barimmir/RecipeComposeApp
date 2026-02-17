package com.example.recipecomposeapp.ui.theme.categories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.ui.theme.ScreenHeader
import com.example.recipecomposeapp.ui.theme.categories.components.CategoryItem
import com.example.recipecomposeapp.ui.theme.categories.model.CategoriesViewModel

@Composable
fun CategoriesScreen(modifier: Modifier = Modifier, viewModel: CategoriesViewModel) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val categories by viewModel.categories.collectAsState()
        ScreenHeader(
            "Категории",
            imagePainter = painterResource(id = R.drawable.bcg_categories),
            contentDescription = "Шапка категорий"
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { categories ->
                CategoryItem(
                    id = categories.id,
                    title = categories.title,
                    descriptionCategory = categories.description,
                    imageUrl = categories.imageUrl,
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    RecipeComposeAppTheme {
        CategoriesScreen(viewModel = CategoriesViewModel())
    }
}
