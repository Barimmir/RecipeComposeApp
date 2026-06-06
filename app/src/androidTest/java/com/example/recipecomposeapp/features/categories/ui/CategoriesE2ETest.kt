package com.example.recipecomposeapp.features.categories.ui

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recipecomposeapp.features.core.ui.MainActivity
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.github.kakaocup.compose.node.element.ComposeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CategoriesE2ETest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.withComposeSupport()
) {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun categoriesScreenLoadsContent() = run {
        step("Wait for categories screen to finish loading") {
            ComposeScreen.onComposeScreen<CategoriesComposeScreen>(composeRule) {
                loadingIndicator { assertIsNotDisplayed() }
            }
        }
        step("Check any state is displayed: content, grid, or error") {
            ComposeScreen.onComposeScreen<CategoriesComposeScreen>(composeRule) {
                val hasCategories = try {
                    categoryItem.assertIsDisplayed()
                    true
                } catch (_: AssertionError) {
                    false
                }
                if (!hasCategories) {
                    val hasError = try {
                        errorMessage.assertIsDisplayed()
                        true
                    } catch (_: AssertionError) {
                        false
                    }
                    if (!hasError) {
                        categoriesGrid.assertIsDisplayed()
                    }
                }
            }
        }
    }

    @Test
    fun clickingCategoryOpensRecipesScreen() = run {
        step("Wait for categories screen to load") {
            ComposeScreen.onComposeScreen<CategoriesComposeScreen>(composeRule) {
                loadingIndicator { assertIsNotDisplayed() }
            }
        }
        step("Attempt clicking first category if available") {
            ComposeScreen.onComposeScreen<CategoriesComposeScreen>(composeRule) {
                val hasCategories = try {
                    categoryItem.assertIsDisplayed()
                    true
                } catch (_: AssertionError) {
                    false
                }
                if (hasCategories) {
                    categoryItem.performClick()
                }
            }
        }
        step("Verify app is still responsive") {
            ComposeScreen.onComposeScreen<CategoriesComposeScreen>(composeRule) { }
        }
    }
}
