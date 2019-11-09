package com.apocalypse_survivors.przepisyapp.repositories

import android.app.Application
import com.apocalypse_survivors.przepisyapp.database.AppDatabase
import com.apocalypse_survivors.przepisyapp.database.DAO.RecipeDAO
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity

class RecipeRepo(application: Application) {

    private var database: AppDatabase = AppDatabase.getInstance(application)
    private lateinit var recipeDAO: RecipeDAO

    init {
        recipeDAO = database.recipeDAO()
    }

    suspend fun insert(recipe: RecipeEntity){
        recipeDAO.insert(recipe)
    }

    suspend fun update(recipe: RecipeEntity){

    }

    suspend fun delete(recipe: RecipeEntity){

    }


}