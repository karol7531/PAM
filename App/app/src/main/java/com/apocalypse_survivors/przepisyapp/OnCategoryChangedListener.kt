package com.apocalypse_survivors.przepisyapp

import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType

interface OnCategoryChangedListener {
    fun onCategoryChanged(newCategory : CategoryType)
}