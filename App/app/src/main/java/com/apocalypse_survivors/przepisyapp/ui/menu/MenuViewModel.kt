package com.apocalypse_survivors.przepisyapp.ui.menu

import android.app.Application
import androidx.lifecycle.*
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryEntity
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.repositories.CategoryRepo
import com.apocalypse_survivors.przepisyapp.repositories.RecipeRepo
import kotlinx.coroutines.launch


class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepo = RecipeRepo(application)
//    private val cat_repo : CategoryRepo = CategoryRepo(application)

//    private fun update(recipe: RecipeEntity) = viewModelScope.launch {
//        repository.update(recipe)
//    }
//
//    private fun delete(recipe: RecipeEntity) = viewModelScope.launch {
//        repository.delete(recipe)
//    }

    fun getAll() : LiveData<List<RecipeEntity>> {
        return repository.getAll()
    }

    fun getAllFromCategory(categoryName : String) : LiveData<List<RecipeEntity>> {
        return repository.getAllFromCategory(categoryName)
    }

//    fun getAllCategories():LiveData<List<CategoryEntity>>{
//        return cat_repo.getAll()
//    }


}