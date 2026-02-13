package com.example.recipecomposeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColorDark,
    onPrimary = TextPrimaryColorDark,
    secondary = AccentColorDark,
    tertiary = AccentBlueDark,
    tertiaryContainer = SliderTrackColorDark,
    background = BackgroundColorDark,
    surface = SurfaceColorDark,
    surfaceVariant = SurfaceVariantColorDark,
    outline = DividerColorDark,
    onSurface = TextPrimaryColorDark,
    onSurfaceVariant = TextSecondaryColorDark,
    primaryContainer = SurfaceColorDark,
    onPrimaryContainer = TextPrimaryColorDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = TextPrimaryColor,
    secondary = AccentColor,
    tertiary = AccentBlue,
    tertiaryContainer = SliderTrackColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    surfaceVariant = SurfaceVariantColor,
    outline = DividerColor,
    onSurface = TextPrimaryColor,
    onSurfaceVariant = TextSecondaryColor,
    primaryContainer = SurfaceColor,
    onPrimaryContainer = TextPrimaryColor
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