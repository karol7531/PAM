package com.apocalypse_survivors.przepisyapp.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.apocalypse_survivors.przepisyapp.database.AppDatabase
import com.apocalypse_survivors.przepisyapp.database.DAO.StepDAO
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity

class StepRepo(application: Application) {

    private var database: AppDatabase = AppDatabase.getInstance(application)
    private var stepDAO: StepDAO

    init {
        stepDAO = database.stepDAO()
    }

    fun deleteSteps(steps: List<StepEntity>){
        stepDAO.delete(steps)
    }

    fun insert(step: StepEntity){
        stepDAO.insert(step)
    }

    fun insert(steps: List<StepEntity>){
        stepDAO.insert(steps)
    }

    fun getAllByRecipeId(recipeId: Int): LiveData<List<StepEntity>> {
        return stepDAO.getAllByRecipeId(recipeId)
    }

    fun getAllByRecipeIdList(recipeId : Int): List<StepEntity>{
        return stepDAO.getAllByRecipeIdList(recipeId)
    }
}