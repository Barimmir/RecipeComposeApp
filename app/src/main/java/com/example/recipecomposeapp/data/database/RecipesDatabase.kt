package com.example.recipecomposeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipecomposeapp.data.database.converter.Converters
import com.example.recipecomposeapp.data.database.dao.CategoryDao
import com.example.recipecomposeapp.data.database.dao.RecipeDao
import com.example.recipecomposeapp.data.database.entity.CategoryEntity
import com.example.recipecomposeapp.data.database.entity.RecipeEntity

@Database(
    entities = [CategoryEntity::class, RecipeEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun recipeDao(): RecipeDao

    companion object {
        fun buildDatabase(context: Context): RecipesDatabase {
            return androidx.room.Room.databaseBuilder(
                context,
                RecipesDatabase::class.java,
                "recipes_database"
            ).fallbackToDestructiveMigration().build()
        }
    }
}
