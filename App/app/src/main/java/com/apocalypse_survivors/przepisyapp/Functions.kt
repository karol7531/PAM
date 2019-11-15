package com.apocalypse_survivors.przepisyapp

import android.content.Context
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType

fun findCategory(label: String, context:Context): CategoryType? {
    CategoryType.values().forEach {
        if(it.isMainCategory && it.getLabel(context) == label)
            return it
    }
    return null
}