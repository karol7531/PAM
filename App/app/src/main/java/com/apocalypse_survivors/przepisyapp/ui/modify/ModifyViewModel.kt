package com.apocalypse_survivors.przepisyapp.ui.modify

import android.R
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.AndroidViewModel
import com.apocalypse_survivors.przepisyapp.common.DateTimeConverter
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity
import com.apocalypse_survivors.przepisyapp.findCategory
import com.apocalypse_survivors.przepisyapp.repositories.RecipeRepo
import com.apocalypse_survivors.przepisyapp.repositories.StepRepo
import java.util.concurrent.Executors

class ModifyViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeRepo: RecipeRepo = RecipeRepo(application)
    private val stepRepo: StepRepo = StepRepo(application)

    internal lateinit var spinnerCategory: String
    internal var imagePath : String = ""

    //TODO: update mode
    fun saveRecipe(name: String, description: String, time: Int, portion:Int, steps : List<StepEntity>, context: Context):Boolean {

        val dbCategory = findCategory(spinnerCategory, context)
        if (name.trim().isEmpty() || dbCategory == null){
            return false
        }

        Executors.newSingleThreadExecutor().execute {
            val created = DateTimeConverter.getDateTime()
            val recipe = RecipeEntity(category_id = dbCategory.name, subcategory_id = dbCategory.name, name = name, description = description,
                image = imagePath, time = time, portion = portion, created = created)

            val recipeId = insert(recipe).toInt()
            Log.d("ModifyViewModel", "recipe saved")

            val correctSteps = prepareSteps(steps, recipeId)

            insertSteps(correctSteps)
            Log.d("ModifyViewModel", "steps saved")
        }

        return true
    }

    // filters out empty steps, sets proper recipeId and number
    private fun prepareSteps(steps: List<StepEntity>, recipeId: Int): List<StepEntity> {
        val correctSteps = steps.filter { !it.description.isNullOrEmpty() }
        for (i in 0 until correctSteps.size) {
            val currentStep = correctSteps[i]
            currentStep.number = i + 1
            currentStep.recipe_id = recipeId
        }
        return correctSteps
    }

    private fun insertSteps(steps: List<StepEntity>) {
        stepRepo.insert(steps)
    }

    private fun insert(recipe: RecipeEntity) : Long{
//        Executors.newSingleThreadExecutor().execute {
            return recipeRepo.insert(recipe)
//        }
    }

    internal fun getSpinnerAdapter(context: Context): ArrayAdapter<String> {
        val categoryLabels = CategoryType.values()
            .filter { it.isMainCategory && it.name != CategoryType.ALL.name }
            .map { it.getLabel(context) }
        return ArrayAdapter(context, R.layout.simple_spinner_item, categoryLabels)
    }
}