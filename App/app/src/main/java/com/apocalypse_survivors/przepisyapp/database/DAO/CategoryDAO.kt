package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.apocalypse_survivors.przepisyapp.database.Entities.CategoryEntity

@Dao
interface CategoryDAO {

    @Insert
    fun save(category : CategoryEntity)

    @Delete
    fun delete(category: CategoryEntity)

    @Query("SELECT * FROM Categories")
    fun getAll() : List<CategoryEntity>
}