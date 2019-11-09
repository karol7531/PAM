package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity

@Dao
interface StepDAO {
    @Insert
    suspend fun insert(step : StepEntity)

    @Update
    suspend fun update(step : StepEntity)

    @Delete
    suspend fun delete(step: StepEntity)

    @Query("SELECT * FROM Steps")
    suspend fun getAll() : LiveData<List<StepEntity>>
}