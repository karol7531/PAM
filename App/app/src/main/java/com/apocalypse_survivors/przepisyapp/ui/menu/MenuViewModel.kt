package com.apocalypse_survivors.przepisyapp.ui.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.repositories.RecipeRepo


class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepo = RecipeRepo(application)
    private var categoryType: CategoryType? = null
    internal var selectedRecipePosition: Int = 0
    internal lateinit var recipes : LiveData<List<RecipeEntity>>

//    private fun delete(recipe: RecipeEntity) = viewModelScope.launch {
//        repository.delete(recipe)
//    }

    internal fun setupData(categoryType: CategoryType?){
        this.categoryType = categoryType
        recipes = if (categoryType == null || categoryType == CategoryType.ALL) {
            getAll()
        } else {
            getAllFromCategory(categoryType.name)
        }
    }

    private fun getAll() : LiveData<List<RecipeEntity>> {
        return repository.getAll()
    }

    private fun getAllFromCategory(categoryName : String) : LiveData<List<RecipeEntity>> {
        return repository.getAllFromCategory(categoryName)
    }
}