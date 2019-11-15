package com.apocalypse_survivors.przepisyapp.ui.modify

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apocalypse_survivors.przepisyapp.common.DateTimeConverter
import com.apocalypse_survivors.przepisyapp.database.entities.RecipeEntity
import com.apocalypse_survivors.przepisyapp.repositories.CategoryRepo
import com.apocalypse_survivors.przepisyapp.repositories.RecipeRepo
import java.util.concurrent.Executors

class ModifyViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepo = RecipeRepo(application)
    private val cat_repo : CategoryRepo = CategoryRepo(application)

    fun saveRecipe(category : String,subcategory : String, name: String, description: String, image : String, time: Int, portion:Int): Boolean {
        val created = DateTimeConverter.getDateTime()
        val recipe = RecipeEntity(category_id = category,subcategory_id = subcategory, name = name, description = description,
            image = image, time = time, portion = portion, created = created)

        insert(recipe)
        return true
    }

    private fun insert(recipe: RecipeEntity) {
        Executors.newSingleThreadExecutor().execute {
            repository.insert(recipe)
        }
    }

//    fun saveCategory(name: String, desc: String){
//        Executors.newSingleThreadExecutor().execute {
//            val category = CategoryEntity(name, desc)
//            cat_repo.insert(category)
//        }
//    }

//    fun saveCategory(name: String, desc: String)  = viewModelScope.launch{
//        val category = CategoryEntity(name, desc)
//        cat_repo.insert(category)
//    }


}