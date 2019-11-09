package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.apocalypse_survivors.przepisyapp.database.Entities.MeasureEntity

@Dao
interface MeasureDAO {

    @Insert
    fun save(category : MeasureEntity)

    @Delete
    fun delete(category: MeasureEntity)

    @Query("SELECT * FROM Measures")
    fun getAll() : List<MeasureEntity>
}