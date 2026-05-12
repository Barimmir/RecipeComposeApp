package com.example.recipecomposeapp.features.recipes.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class IngredientsUiModel(
    val name: String,
    val amount: String,
    val unitOfMeasure: String
) : Parcelable