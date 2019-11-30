package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity

@Dao
interface StepDAO {
    @Insert
    fun insert(step : StepEntity)

    @Insert
    fun insert(steps: List<StepEntity>)

//    @Update
//    fun update(step : StepEntity)
//
//    @Delete
//    fun delete(step: StepEntity)

    @Query("SELECT * FROM Steps")
    fun getAll() : LiveData<List<StepEntity>>

    @Query("SELECT * FROM Steps WHERE recipe_id = :recipeId ORDER BY number")
    fun getAllByRecipeId(recipeId: Int): LiveData<List<StepEntity>>
}