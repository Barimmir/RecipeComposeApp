package com.example.recipecomposeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    secondary = AccentColorDark,
    tertiary = AccentBlueDark,
    tertiaryContainer = SliderTrackColorDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    surfaceVariant = SurfaceVariantColorDark,
    outline = DividerColorDark,
    onSurface = TextPrimaryColorDark,
    onSurfaceVariant = TextSecondaryColorDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    secondary = AccentColor,
    tertiary = AccentBlue,
    tertiaryContainer = SliderTrackColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    surfaceVariant = SurfaceVariantColor,
    outline = DividerColor,
    onSurface = TextPrimaryColor,
    onSurfaceVariant = TextSecondaryColor

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun RecipeComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = recipeAppTypography,
        content = content
    )
}