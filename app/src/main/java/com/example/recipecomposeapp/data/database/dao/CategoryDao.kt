package com.example.recipecomposeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY category_name")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}
