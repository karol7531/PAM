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
    internal var steps: List<StepEntity> = listOf()
    internal lateinit var recipe : RecipeEntity
    internal var recipeId : Int = 0

    //WARN: recipe can not be initialized
    internal fun getSteps(): LiveData<List<StepEntity>> {
        return stepRepo.getAllByRecipeId(recipe.id)
    }

    internal fun getRecipe():LiveData<RecipeEntity>{
        return recipeRepo.getRecipe(recipeId)
    }

    //WARN: recipe can not be initialized
    internal fun getDescText(): String {
        val builder = StringBuilder()
        builder.append(recipe.name).append("\n\n")
            .append(recipe.description).append("\n\n")
        steps.forEach { step -> builder.append(step.number).append(". ").append(step.description).append("\n\n") }

        return builder.toString()
    }

//    private fun insert(step: StepEntity) {
//        Executors.newSingleThreadExecutor().execute {
//            stepRepo.insert(step)
//        }
//    }
}