package com.example.recipecomposeapp.ui.theme.recipes.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.android.parcel.Parcelize

@Immutable
@Parcelize
data class IngredientUiModel(
    val name: String,
    val amount: String
) : Parcelable