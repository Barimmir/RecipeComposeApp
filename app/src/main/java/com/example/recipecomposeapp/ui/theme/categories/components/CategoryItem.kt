package com.example.recipecomposeapp.ui.theme.categories.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.recipecomposeapp.Dimens
import com.example.recipecomposeapp.R
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme

@Composable
fun CategoryItem(
    id: Int,
    title: String,
    descriptionCategory: String,
    imageUrl: String,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(Dimens.TWO_HUNDRED_TWENTY_DP)
            .clickable { onClick(id) }
            .background(MaterialTheme.colorScheme.background),
        shape = RoundedCornerShape(Dimens.EIGHT_DP)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Dimens.WEIGHT_ONE_F)
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.ONE_HUNDRED_TWENTY_DP),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.img_placeholder),
                error = painterResource(R.drawable.img_error)
            )
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.SIXTEEN_DP,
                        vertical = Dimens.EIGHT_DP
                    )
            )
            Text(
                text = descriptionCategory,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = Dimens.SIXTEEN_DP,
                        vertical = Dimens.EIGHT_DP
                    ),
                maxLines = 3
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
                id = 0,
                title = "Бургеры",
                descriptionCategory = "Рецепты всех популярных бургеров видов бургеров",
                imageUrl = "",
                onClick = {},
                modifier = Modifier.fillMaxWidth(Dimens.HALF_ONE_F)
            )
        }
    }
}
