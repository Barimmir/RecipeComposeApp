package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class CategoriesComposeScreen(
    interactionProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<CategoriesComposeScreen>(
    interactionProvider,
    viewBuilderAction = { hasTestTag("categories_screen") }
) {
    val loadingIndicator = child<KNode> { hasTestTag("loading_indicator") }
    val categoryItem = child<KNode> { hasTestTag("category_item") }
    val errorMessage = child<KNode> { hasTestTag("error_message") }
    val categoriesGrid = child<KNode> { hasTestTag("categories_grid") }
}
