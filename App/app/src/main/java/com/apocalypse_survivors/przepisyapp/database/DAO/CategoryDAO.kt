package com.apocalypse_survivors.przepisyapp.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryEntity

@Dao
interface CategoryDAO {

    @Insert
    fun insert(category : CategoryEntity)

    @Insert
    fun insert(categoryList : List<CategoryEntity>)

//    @Update
//    fun update(category : CategoryEntity)
//
//    @Delete
//    fun delete(category: CategoryEntity)

    @Query("SELECT * FROM Categories")
    fun getAll() : LiveData<List<CategoryEntity>>
}