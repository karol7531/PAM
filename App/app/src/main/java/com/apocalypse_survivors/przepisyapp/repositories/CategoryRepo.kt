package com.apocalypse_survivors.przepisyapp.repositories

import android.app.Application
import com.apocalypse_survivors.przepisyapp.database.AppDatabase
import com.apocalypse_survivors.przepisyapp.database.DAO.CategoryDAO
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryEntity

class CategoryRepo(application: Application){

    private var database: AppDatabase = AppDatabase.getInstance(application)
    private lateinit var categoryDAO: CategoryDAO

    init {
        categoryDAO = database.categoryDAO()
    }

    suspend fun insert(recipe: CategoryEntity){
        categoryDAO.insert(recipe)
    }

    suspend fun update(recipe: CategoryEntity){

    }

    suspend fun delete(recipe: CategoryEntity){

    }

}