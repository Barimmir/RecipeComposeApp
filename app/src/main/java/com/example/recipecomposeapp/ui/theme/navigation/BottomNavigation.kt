package com.example.recipecomposeapp.ui.theme.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipecomposeapp.Dimens
import com.example.recipecomposeapp.ui.theme.RecipeComposeAppTheme
import com.example.recipecomposeapp.util.FavoriteDataStoreManager
import kotlinx.coroutines.flow.flowOf

@Composable
fun BottomNavigation(
    onCategoriesClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onRecipesClick: () -> Unit,
    favoriteDataStoreManager: FavoriteDataStoreManager? = null
) {
    val favoriteCount by (favoriteDataStoreManager?.getFavoriteCountFlow() ?: flowOf(0))
        .collectAsState(initial = 0)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = onCategoriesClick,
            modifier = Modifier.weight(Dimens.WEIGHT_ONE_F),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                "Категории",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayLarge
            )
        }
        Button(
            onClick = onFavoriteClick,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = MaterialTheme.colorScheme.surface
            )
        ) {
            BadgedBox(
                badge = {
                    if (favoriteCount > 0) {
                        Badge(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.surface
                        ) {
                            Text(
                                text = if (favoriteCount > 99) "99+" else favoriteCount.toString(),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.displayLarge
                            )
                        }
                    }
                }
            ) {
                Text(
                    "Избранные",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    RecipeComposeAppTheme {
        BottomNavigation(
            onCategoriesClick = {},
            onFavoriteClick = {},
            onRecipesClick = {},
            favoriteDataStoreManager = null
        )
    }
}