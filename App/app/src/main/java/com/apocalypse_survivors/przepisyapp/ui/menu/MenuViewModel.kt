package com.apocalypse_survivors.przepisyapp.ui.menu

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.repositories.RecipeRepo


class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepo = RecipeRepo(application)
    internal var categoryType: CategoryType? = null

//    private fun delete(recipe: RecipeEntity) = viewModelScope.launch {
//        repository.delete(recipe)
//    }

    fun getAll() : LiveData<List<RecipeEntity>> {
        return repository.getAll()
    }

    fun getAllFromCategory(categoryName : String) : LiveData<List<RecipeEntity>> {
        return repository.getAllFromCategory(categoryName)
    }
}