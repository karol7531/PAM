package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity

@Dao
interface RecipeDAO {
    @Insert
    fun insert(recipe : RecipeEntity):Long

//    @Update
//    fun update(recipe : RecipeEntity)

    @Delete
    fun delete(recipe: RecipeEntity)

    @Query("SELECT * FROM Recipes ORDER BY name")
    fun getAll() : LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM Recipes WHERE category_id = :categoryName ORDER BY name")
    fun getAllFromCategory(categoryName : String) : LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM Recipes Where id = :recipeId")
    fun getRecipe(recipeId: Int): LiveData<RecipeEntity>
}