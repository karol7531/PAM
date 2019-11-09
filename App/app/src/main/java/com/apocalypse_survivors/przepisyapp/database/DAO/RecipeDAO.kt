package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity

@Dao
interface RecipeDAO {
    @Insert
    suspend fun insert(recipe : RecipeEntity)

    @Update
    suspend fun update(recipe : RecipeEntity)

    @Delete
    suspend fun delete(recipe: RecipeEntity)

    @Query("SELECT * FROM Recipes")
    suspend fun getAll() : LiveData<List<RecipeEntity>>
}