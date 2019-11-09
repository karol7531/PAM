package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.apocalypse_survivors.przepisyapp.database.Entities.RecipeEntity

@Dao
interface RecipeDAO {
    @Insert
    fun save(category : RecipeEntity)

    @Delete
    fun delete(category: RecipeEntity)

    @Query("SELECT * FROM Recipes")
    fun getAll() : List<RecipeEntity>
}