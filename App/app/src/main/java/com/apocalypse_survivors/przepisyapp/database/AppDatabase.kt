package com.apocalypse_survivors.przepisyapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apocalypse_survivors.przepisyapp.database.DAO.*
import com.apocalypse_survivors.przepisyapp.database.Entities.*

@Database(entities = [CategoryEntity::class, IngredientEntity::class, MeasureEntity::class, RecipeEntity::class, StepEntity::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDAO() : CategoryDAO

    abstract fun ingredientDAO() : IngredientDAO

    abstract fun measureDAO() : MeasureDAO

    abstract fun recipeDAO() : RecipeDAO

    abstract fun stepDAO() : StepDAO
}