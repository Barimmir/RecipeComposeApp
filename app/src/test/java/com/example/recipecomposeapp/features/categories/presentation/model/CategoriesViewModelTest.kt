package com.example.recipecomposeapp.features.categories.presentation.model

import app.cash.turbine.test
import com.example.recipecomposeapp.data.model.repository.RecipesRepository
import com.example.recipecomposeapp.features.core.utils.Constants
import com.example.recipecomposeapp.fixtures.CategoryTestFixtures
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesViewModelTest {

    private val repository: RecipesRepository = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `loads categories from repository`() = runTest(testDispatcher) {
        val categoryDtoList = CategoryTestFixtures.createCategoryDtoList()
        every { repository.getCategories() } returns flowOf(categoryDtoList)

        val viewModel = CategoriesViewModel(repository)

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(2, state.categories.size)
            assertEquals(false, state.isLoading)
            assertNull(state.error)
            assertTrue(state.categories[0].imageUrl.startsWith(Constants.IMAGES_BASE_URL))
            verify(exactly = 1) { repository.getCategories() }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows empty list when repository returns no data`() = runTest(testDispatcher) {
        every { repository.getCategories() } returns flowOf(emptyList())

        val viewModel = CategoriesViewModel(repository)

        viewModel.uiState.test {
            val state = awaitItem()

            assertTrue(state.categories.isEmpty())
            assertNull(state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `shows error when repository throws`() = runTest(testDispatcher) {
        every { repository.getCategories() } returns flow {
            throw IOException("Network error")
        }

        val viewModel = CategoriesViewModel(repository)

        viewModel.uiState.test {
            val state = awaitItem()

            assertEquals(false, state.isLoading)
            assertNotNull(state.error)
            assertEquals("Network error", state.error)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
