package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apocalypse_survivors.przepisyapp.database.entities.MeasureEntity

@Dao
interface MeasureDAO {

    @Insert
    fun insert(measure : MeasureEntity)

    @Insert
    fun insert(measureList : List<MeasureEntity>)

//    @Update
//    fun update(measure : MeasureEntity)
//
//    @Delete
//    fun delete(measure: MeasureEntity)

    @Query("SELECT * FROM Measures")
    fun getAll() : LiveData<List<MeasureEntity>>
}