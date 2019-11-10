package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity

@Dao
interface StepDAO {
    @Insert
    fun insert(step : StepEntity)

//    @Update
//    fun update(step : StepEntity)
//
//    @Delete
//    fun delete(step: StepEntity)

    @Query("SELECT * FROM Steps")
    fun getAll() : LiveData<List<StepEntity>>
}