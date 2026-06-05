package com.example.recipecomposeapp.data.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.cash.turbine.test
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoritesDataStoreTest {
    private lateinit var context: Context
    private lateinit var manager: FavoriteDataStoreManager

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        manager = FavoriteDataStoreManager(context)
    }

    @After
    fun tearDown() {
        runTest {
            context.dataStore.edit { it.clear() }
        }
    }

    @Test
    fun addFavoriteSavesRecipeId() = runTest {
        manager.addFavorite(42)
        val favorites = manager.getFavoriteIdsFlow().first()
        assertTrue(favorites.contains("42"))
    }

    @Test
    fun removeFromFavoritesDeletesRecipeId() = runTest {
        manager.addFavorite(42)
        manager.removeFavorite(42)
        val favorites = manager.getFavoriteIdsFlow().first()
        assertFalse(favorites.contains("42"))
    }

    @Test
    fun favoritesFlowEmitsUpdatesReactively() = runTest {
        manager.getFavoriteIdsFlow().test {
            skipItems(1) // skip initial empty state
            manager.addFavorite(1)
            val emitted = awaitItem()
            assertTrue(emitted.contains("1"))
            cancelAndIgnoreRemainingEvents()
        }
    }
}