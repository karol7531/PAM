package com.apocalypse_survivors.przepisyapp.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.apocalypse_survivors.przepisyapp.database.AppDatabase
import com.apocalypse_survivors.przepisyapp.database.DAO.CategoryDAO
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryEntity

class CategoryRepo(application: Application){

    private var database: AppDatabase = AppDatabase.getInstance(application)
    private var categoryDAO: CategoryDAO

    init {
        categoryDAO = database.categoryDAO()
    }

    fun insert(recipe: CategoryEntity){
        categoryDAO.insert(recipe)
    }

//    fun update(recipe: CategoryEntity){
//
//    }
//
//    fun delete(recipe: CategoryEntity){
//
//    }

    fun getAll(): LiveData<List<CategoryEntity>> {
        return categoryDAO.getAll()
    }
}