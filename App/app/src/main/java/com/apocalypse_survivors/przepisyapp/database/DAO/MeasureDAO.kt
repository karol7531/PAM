package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apocalypse_survivors.przepisyapp.database.entities.MeasureEntity

@Dao
interface MeasureDAO {

    @Insert
    suspend fun insert(measure : MeasureEntity)

    @Insert
    fun insert(measureList : List<MeasureEntity>)

    @Update
    suspend fun update(measure : MeasureEntity)

    @Delete
    suspend fun delete(measure: MeasureEntity)

    @Query("SELECT * FROM Measures")
    fun getAll() : LiveData<List<MeasureEntity>>
}