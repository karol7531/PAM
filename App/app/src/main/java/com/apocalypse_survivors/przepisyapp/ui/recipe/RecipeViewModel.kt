package com.apocalypse_survivors.przepisyapp.ui.recipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity
import com.apocalypse_survivors.przepisyapp.repositories.RecipeRepo
import com.apocalypse_survivors.przepisyapp.repositories.StepRepo

class RecipeViewModel(application: Application) :  AndroidViewModel(application) {

    private val stepRepo: StepRepo = StepRepo(application)
    private val recipeRepo: RecipeRepo = RecipeRepo(application)
    private lateinit var steps: List<StepEntity>
    private lateinit var recipe : RecipeEntity

    //WARN: recipe can not be initialized or be null
    internal fun getSteps(): LiveData<List<StepEntity>> {
        return stepRepo.getAllByRecipeId(recipe.id)
    }

    internal fun setSteps(steps: List<StepEntity>) {
        this.steps = steps
    }

    internal fun setRecipe(recipeId: Int) {
        //WARN: null check
        recipe = recipeRepo.getRecipe(recipeId)
    }

//    private fun insert(step: StepEntity) {
//        Executors.newSingleThreadExecutor().execute {
//            stepRepo.insert(step)
//        }
//    }
}