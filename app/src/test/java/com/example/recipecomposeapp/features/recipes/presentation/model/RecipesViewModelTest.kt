package com.example.recipecomposeapp.features.recipes.presentation.model

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.recipecomposeapp.data.model.repository.RecipesRepository
import com.example.recipecomposeapp.fixtures.RecipeTestFixtures
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest {

    private val repository: RecipesRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle()
    ): RecipesViewModel {
        return RecipesViewModel(savedStateHandle, repository)
    }

    @Test
    fun `loads recipes for category`() = runTest(testDispatcher) {
        val savedStateHandle = SavedStateHandle(
            mapOf("categoryId" to 1, "categoryTitle" to "Завтраки", "categoryImageUrl" to "cat.png")
        )
        val recipeDtoList = RecipeTestFixtures.createRecipeDtoList()
        every { repository.getRecipesByCategory(1) } returns flowOf(recipeDtoList)

        val viewModel = createViewModel(savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(2, state.recipes.size)
            assertEquals("Завтраки", state.categoryTitle)
            assertEquals(false, state.isLoading)
            assertEquals(null, state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state reflects category title from savedState`() = runTest(testDispatcher) {
        val savedStateHandle = SavedStateHandle(
            mapOf("categoryId" to 1, "categoryTitle" to "Завтраки", "categoryImageUrl" to "cat.png")
        )
        every { repository.getRecipesByCategory(1) } returns flowOf(emptyList())

        val viewModel = createViewModel(savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals("Завтраки", state.categoryTitle)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest(testDispatcher) {
        val savedStateHandle = SavedStateHandle(
            mapOf("categoryId" to 1, "categoryTitle" to "Завтраки", "categoryImageUrl" to "cat.png")
        )
        every { repository.getRecipesByCategory(1) } returns flow {
            throw IOException("Network error")
        }

        val viewModel = createViewModel(savedStateHandle)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(false, state.isLoading)
            assertNotNull(state.error)
            assertEquals("Network error", state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
