package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.apocalypse_survivors.przepisyapp.database.Entities.IngredientEntity

@Dao
interface IngredientDAO {

    @Insert
    fun save(category : IngredientEntity)

    @Delete
    fun delete(category: IngredientEntity)

    @Query("SELECT * FROM Ingredients")
    fun getAll() : List<IngredientEntity>
}