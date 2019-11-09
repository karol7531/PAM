package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.apocalypse_survivors.przepisyapp.database.Entities.StepEntity

@Dao
interface StepDAO {
    @Insert
    fun save(category : StepEntity)

    @Delete
    fun delete(category: StepEntity)

    @Query("SELECT * FROM Steps")
    fun getAll() : List<StepEntity>
}