package com.example.recipecomposeapp.ui.theme

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipecomposeapp.Dimens
import com.example.recipecomposeapp.R

@Composable
fun ScreenHeader(
    title: String,
    imagePainter: Painter,
    contentDescription: String,
    showShareButton: Boolean,
    onShareClick: () -> Unit,
    isFavorite: Boolean,
    showFavoriteButton: Boolean,
    onFavoriteClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.TWO_HUNDRED_DP),
        contentAlignment = Alignment.TopStart
    ) {
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        if (showFavoriteButton) {
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Dimens.SIXTEEN_DP)
            ) {
                Crossfade(
                    targetState = isFavorite,
                    animationSpec = tween(durationMillis = 300),
                    label = "favorite_animation"
                ) { isCurrentlyFavorite ->
                    Icon(
                        painter = painterResource(
                            id = if (isCurrentlyFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty
                        ),
                        contentDescription = if (isCurrentlyFavorite) "Удалить из избранного" else "Добавить в избранное",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
        Surface(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = Dimens.SIXTEEN_DP, bottom = Dimens.SIXTEEN_DP),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
            shape = RoundedCornerShape(Dimens.TWELVE_DP)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = Dimens.TWENTY_EIGHT_SP
            )
        }
    }
}

@Preview
@Composable
fun ScreenHeaderPreview() {
    RecipeComposeAppTheme {
        ScreenHeader(
            "Категории",
            imagePainter = painterResource(id = R.drawable.bcg_categories),
            contentDescription = "",
            showShareButton = true,
            onShareClick = {},
            showFavoriteButton = true,
            isFavorite = true,
            onFavoriteClick = {}
        )
    }

}