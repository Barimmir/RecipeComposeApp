package com.example.recipecomposeapp.features.recipes.ui

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class RecipesComposeScreen(
    interactionProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<RecipesComposeScreen>(
    interactionProvider,
    viewBuilderAction = { hasTestTag("recipes_screen") }
) {
    val loadingIndicator = child<KNode> { hasTestTag("loading_indicator") }
    val emptyState = child<KNode> { hasTestTag("empty_state") }
    val errorMessage = child<KNode> { hasTestTag("error_message") }
}
