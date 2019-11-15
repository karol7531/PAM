package com.apocalypse_survivors.przepisyapp.ui.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType

class MainActivityViewModel(application: Application) : AndroidViewModel(application)  {

//    private val cat_repo : CategoryRepo = CategoryRepo(application)

    var categorySelected : CategoryType? = null
        internal set
}