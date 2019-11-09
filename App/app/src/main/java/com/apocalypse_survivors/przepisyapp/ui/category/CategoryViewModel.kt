package com.apocalypse_survivors.przepisyapp.ui.category

import android.app.Application
import androidx.lifecycle.*
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryEntity
import com.apocalypse_survivors.przepisyapp.repositories.CategoryRepo
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CategoryRepo

    init {
        repository = CategoryRepo(application)
    }

    fun insert(categoryEntity: CategoryEntity) = viewModelScope.launch {
        repository.insert(categoryEntity)
    }


}