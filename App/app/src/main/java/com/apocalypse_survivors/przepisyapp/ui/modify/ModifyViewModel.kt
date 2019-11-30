package com.apocalypse_survivors.przepisyapp.ui.modify

import android.R
import android.app.Application
import android.content.Context
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

            val correctSteps = steps.filter { !it.description.isNullOrEmpty() }
            correctSteps.forEach { it.recipe_id = recipeId }

            insertSteps(correctSteps)
        }

        return true
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
            .filter { it.isMainCategory }
            .map { it.getLabel(context!!) }
        return ArrayAdapter(context, R.layout.simple_spinner_item, categoryLabels)
    }
}