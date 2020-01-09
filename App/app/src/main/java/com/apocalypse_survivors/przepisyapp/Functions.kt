package com.apocalypse_survivors.przepisyapp

import android.content.Context
import android.util.TypedValue
import com.apocalypse_survivors.przepisyapp.database.entities.CategoryType


fun findCategory(label: String, context:Context): CategoryType? {
    CategoryType.values().forEach {
        if(it.isMainCategory && it.getLabel(context) == label)
            return it
    }
    CategoryType.values().forEach {
        if(it.isMainCategory && it.name == label){
            return it
        }
    }
    return null
}

fun getPixel(dp:Float, context:Context) : Int{
    val r = context.resources
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        r.displayMetrics
    ).toInt()
}