package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen

class RecipesComposeScreen(
    interactionProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<RecipesComposeScreen>(
    interactionProvider,
    viewBuilderAction = { hasTestTag("recipes_screen") }
)
