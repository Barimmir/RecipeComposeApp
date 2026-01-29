package com.example.recipecomposeapp.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.recipecomposeapp.R

val montserratFontFamily =
    FontFamily(
        listOf(
            Font(
                resId = R.font.montserrat_italic_variablefont_wght,
                weight = FontWeight.Normal
            ),
            Font(
                resId = R.font.montserrat_variablefont_wght,
                weight = FontWeight.Normal
            )
        )
    )
val montserratAlternatesFontFamily = FontFamily(
    listOf(
        Font(
            resId = R.font.montserrat_alternates_bold,
            weight = FontWeight.Bold,
        ),
        Font(
            resId = R.font.montserrat_alternates_thin,
            weight = FontWeight.Thin,
        ),
        Font(
            resId = R.font.montserrat_alternates_black,
            weight = FontWeight.Black,
        ),
        Font(
            resId = R.font.montserrat_alternates_italic,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.montserrat_alternates_light,
            weight = FontWeight.Light,
        ),
        Font(
            resId = R.font.montserrat_alternates_medium,
            weight = FontWeight.Medium,
        ),
        Font(
            resId = R.font.montserrat_alternates_regular,
        ),
        Font(
            resId = R.font.montserrat_alternates_semi_bold,
            weight = FontWeight.SemiBold,
        ),
        Font(
            resId = R.font.montserrat_alternates_thin_italic,
            weight = FontWeight.Thin,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.montserrat_alternates_medium_italic,
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.montserrat_alternates_semi_bold_italic,
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.montserrat_alternates_light_italic,
            weight = FontWeight.Light,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.montserrat_alternates_extra_light,
            weight = FontWeight.ExtraLight,
        ),
        Font(
            resId = R.font.montserrat_alternates_extra_bold_italic,
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.montserrat_alternates_bold_italic,
            weight = FontWeight.Bold,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.montserrat_alternates_black_italic,
            weight = FontWeight.Black,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.montserrat_italic_variablefont_wght,
            style = FontStyle.Italic
        ),
        Font(
            resId = R.font.montserrat_variablefont_wght,
        )
    )
)