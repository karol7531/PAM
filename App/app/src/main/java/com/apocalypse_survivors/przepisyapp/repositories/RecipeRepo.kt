package com.apocalypse_survivors.przepisyapp.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.apocalypse_survivors.przepisyapp.database.AppDatabase
import com.apocalypse_survivors.przepisyapp.database.DAO.RecipeDAO
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity

class RecipeRepo(application: Application) {

    private var database: AppDatabase = AppDatabase.getInstance(application)
    private var recipeDAO: RecipeDAO

    init {
        recipeDAO = database.recipeDAO()
    }

    fun insert(recipe: RecipeEntity){
        recipeDAO.insert(recipe)
    }

//    fun update(recipe: RecipeEntity){
//
//    }
//
//    fun delete(recipe: RecipeEntity){
//
//    }

    fun getAll(): LiveData<List<RecipeEntity>> {
        return recipeDAO.getAll()
    }

    fun getAllFromCategory(categoryName: String): LiveData<List<RecipeEntity>> {
        return recipeDAO.getAllFromCategory(categoryName)
    }

    fun getRecipe(recipeId: Int): RecipeEntity {
        return recipeDAO.getRecipe(recipeId)
    }


}