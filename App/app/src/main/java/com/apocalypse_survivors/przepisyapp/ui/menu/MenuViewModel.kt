package com.apocalypse_survivors.przepisyapp.ui.menu

import android.app.Application
import androidx.lifecycle.*
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.repositories.RecipeRepo
import kotlinx.coroutines.launch


class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepo = RecipeRepo(application)

    fun insert(recipe: RecipeEntity) = viewModelScope.launch {
        repository.insert(recipe)
    }

    fun update(recipe: RecipeEntity) = viewModelScope.launch {
        repository.update(recipe)
    }

    fun delete(recipe: RecipeEntity) = viewModelScope.launch {
        repository.delete(recipe)
    }

    fun getAllFromCategory(categoryName : String) : LiveData<List<RecipeEntity>> {
        return repository.getAllFromCategory(categoryName)
    }


}