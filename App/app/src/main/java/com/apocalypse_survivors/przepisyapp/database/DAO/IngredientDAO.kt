package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apocalypse_survivors.przepisyapp.database.entities.IngredientEntity

@Dao
interface IngredientDAO {

    @Insert
    suspend fun insert(ingredient : IngredientEntity)

    @Update
    suspend fun update(ingredient : IngredientEntity)

    @Delete
    suspend fun delete(ingredient: IngredientEntity)

    @Query("SELECT * FROM Ingredients")
    suspend fun getAll() : LiveData<List<IngredientEntity>>
}