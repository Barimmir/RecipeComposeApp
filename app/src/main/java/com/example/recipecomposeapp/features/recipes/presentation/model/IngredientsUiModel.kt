package com.example.recipecomposeapp.features.recipes.presentation.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.android.parcel.Parcelize

@Immutable
@Parcelize
data class IngredientsUiModel(
    val name: String,
    val amount: Float,
    val unitOfMeasure: String
) : Parcelable