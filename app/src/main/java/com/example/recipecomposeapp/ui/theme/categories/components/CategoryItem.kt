package com.example.recipecomposeapp.ui.theme.categories.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.recipecomposeapp.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.ui.theme.categories.model.CategoryUiModel

@Composable
fun CategoryItem(
    title: String,
    descriptionCategory: String,
    imageUrl: String,
    onClick: (CategoryUiModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.CATEGORY_IMAGE_HEIGHT),
                contentScale = ContentScale.Crop
            )
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = descriptionCategory,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    RecipeComposeAppTheme {
        Column {
            CategoryItem(
                title = "Бургеры",
                descriptionCategory = "Рецепты всех популярных бургеров",
                imageUrl = "file:///android_asset/burger.png",
                onClick = {},
                modifier = Modifier.fillMaxWidth(0.5f)
            )
        }
    }
}
