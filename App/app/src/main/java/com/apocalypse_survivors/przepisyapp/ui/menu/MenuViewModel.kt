package com.apocalypse_survivors.przepisyapp.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.database.entities.StepEntity
import com.apocalypse_survivors.przepisyapp.repositories.RecipeRepo
import com.apocalypse_survivors.przepisyapp.repositories.StepRepo
import java.util.concurrent.Executors


class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeRepo: RecipeRepo = RecipeRepo(application)
    private val stepRepo: StepRepo = StepRepo(application)
    private var categoryType: CategoryType? = null
    internal var selectedRecipePosition: Int = 0
    internal lateinit var recipes : LiveData<List<RecipeEntity>>
    private var recentlyDeleted : RecentlyDeleted? = null

    private class RecentlyDeleted(val recipe:RecipeEntity, val steps:List<StepEntity>)

    internal fun delete(recipe: RecipeEntity) {
        Executors.newSingleThreadExecutor().execute {
            val steps = stepRepo.getAllByRecipeIdList(recipeId = recipe.id)
            //WARN: before delete of recipe delete everything which is linked to that recipe
            stepRepo.deleteSteps(steps)
            Log.d("MenuViewModel", "steps for recipe ${recipe.id} deleted")
            recipeRepo.delete(recipe)
            Log.d("MenuViewModel", "recipe deleted ${recipe.id}")
            recentlyDeleted = RecentlyDeleted(recipe, steps)
        }
    }

    internal fun reviveRecentlyDeleted(){
        Executors.newSingleThreadExecutor().execute {
            val clone = recentlyDeleted
            recentlyDeleted = null
            if (clone != null) {
                //WARN: firstly recipe than everything linked to it
                recipeRepo.insert(clone.recipe)
                stepRepo.insert(clone.steps)
                Log.d("MenuViewModel", "recipe revived")
            }
        }
    }

    internal fun setupData(categoryType: CategoryType?){
        this.categoryType = categoryType
        recipes = if (categoryType == null || categoryType == CategoryType.ALL) {
            getAll()
        } else {
            getAllFromCategory(categoryType.name)
        }
    }

    private fun getAll() : LiveData<List<RecipeEntity>> {
        return recipeRepo.getAll()
    }

    private fun getAllFromCategory(categoryName : String) : LiveData<List<RecipeEntity>> {
        return recipeRepo.getAllFromCategory(categoryName)
    }
}