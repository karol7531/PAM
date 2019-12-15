package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apocalypse_survivors.przepisyapp.database.entities.IngredientEntity

@Dao
interface IngredientDAO {

    @Insert
    suspend fun insert(ingredient : IngredientEntity)

//    @Update
//    fun update(ingredient : IngredientEntity)
//
//    @Delete
//    fun delete(ingredient: IngredientEntity)

    @Query("SELECT * FROM Ingredients")
    fun getAll() : LiveData<List<IngredientEntity>>
}