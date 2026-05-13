package com.example.recipecomposeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipecomposeapp.data.database.dao.CategoryDao
import com.example.recipecomposeapp.data.database.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

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
