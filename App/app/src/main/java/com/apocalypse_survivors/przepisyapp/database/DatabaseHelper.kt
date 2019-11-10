package com.apocalypse_survivors.przepisyapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.apocalypse_survivors.przepisyapp.database.DAO.CategoryType
import com.apocalypse_survivors.przepisyapp.database.DAO.MeasureType
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DB_VERSION = 1
        const val DB_NAME = "RecipesDB.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(ReaderContract.Categories.SQL_CREATE_CATEGORIES)
        db!!.execSQL(ReaderContract.Measures.SQL_CREATE_MEASURES)
        db!!.execSQL(ReaderContract.Recipes.SQL_CREATE_RECIPES)
        db!!.execSQL(ReaderContract.Steps.SQL_CREATE_STEPS)
        db!!.execSQL(ReaderContract.Ingredients.SQL_CREATE_INGREDIENTS)
        insertCategories()
        insertMeasures()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(ReaderContract.Ingredients.SQL_DELETE_INGREDIENTS)
        db!!.execSQL(ReaderContract.Steps.SQL_DELETE_STEPS)
        db!!.execSQL(ReaderContract.Recipes.SQL_DELETE_RECIPES)
        db!!.execSQL(ReaderContract.Measures.SQL_DELETE_MEASURES)
        db!!.execSQL(ReaderContract.Categories.SQL_DELETE_CATEGORIES)
        onCreate(db)
    }

    //CategoryEntity
    private fun insertCategory(name:String, desc:String?):Long{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ReaderContract.Categories.COL_NAME, name)
            if (desc != null) {
                put(ReaderContract.Categories.COL_DESCRIPTION, desc)
            }
        }
        return db.insert(ReaderContract.Categories.TABLE_NAME, null, values)
    }

    private fun insertCategories(){
        enumValues<CategoryType>().forEach {
            insertCategory(it.name, null)
        }
    }

    //Measure
    private fun insertMeasure(name:String):Long{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ReaderContract.Measures.COL_NAME, name)
        }
        return db.insert(ReaderContract.Measures.TABLE_NAME, null, values)
    }

    private fun insertMeasures(){
        enumValues<MeasureType>().forEach {
            insertMeasure(it.name)
        }
    }

    //Recipe
    fun insertRecipe(name: String, category: CategoryType, subcategory: CategoryType, desc: String, image: String, time: Int, portion: Int): Long{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ReaderContract.Recipes.COL_NAME, name)
            put(ReaderContract.Recipes.COL_CATEGORY, category.toString())
            put(ReaderContract.Recipes.COL_SUBCATEGORY, subcategory.toString())
            put(ReaderContract.Recipes.COL_DESCRIPTION, desc)
            put(ReaderContract.Recipes.COL_IMAGE, image)
            put(ReaderContract.Recipes.COL_TIME, time)
            put(ReaderContract.Recipes.COL_PORTION, portion)
            put(ReaderContract.Recipes.COL_CREATED, getDateTime())
        }
        return db.insert(ReaderContract.Recipes.TABLE_NAME, null, values)
    }

    fun updateRecipe(id: Long, name: String, category: CategoryType, subcategory: CategoryType, desc: String, image: String, time: Int, portion: Int): Int{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ReaderContract.Recipes.COL_NAME, name)
            put(ReaderContract.Recipes.COL_CATEGORY, category.toString())
            put(ReaderContract.Recipes.COL_SUBCATEGORY, subcategory.toString())
            put(ReaderContract.Recipes.COL_DESCRIPTION, desc)
            put(ReaderContract.Recipes.COL_IMAGE, image)
            put(ReaderContract.Recipes.COL_TIME, time)
            put(ReaderContract.Recipes.COL_PORTION, portion)
            put(ReaderContract.Recipes.COL_CREATED, getDateTime())
        }
        return db.update(ReaderContract.Recipes.TABLE_NAME, values, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }

    //Steps
    fun insertSteps(recipeId: Int, number: Int, desc: String): Long{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ReaderContract.Steps.COL_RECIPE, recipeId)
            put(ReaderContract.Steps.COL_NUMBER, number)
            put(ReaderContract.Steps.COL_DESCRIPTION, desc)
        }
        return db.insert(ReaderContract.Steps.TABLE_NAME, null, values)
    }

    fun updateSteps(id: Long, recipeId: Int, number: Int, desc: String): Int{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ReaderContract.Steps.COL_RECIPE, recipeId)
            put(ReaderContract.Steps.COL_NUMBER, number)
            put(ReaderContract.Steps.COL_DESCRIPTION, desc)
        }
        return db.update(ReaderContract.Steps.TABLE_NAME, values, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }

    //Ingredients
    fun insertIngredients(recipeId: Int, amount: Int, product: String, measure: MeasureType): Long{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ReaderContract.Ingredients.COL_RECIPE, recipeId)
            put(ReaderContract.Ingredients.COL_AMOUNT, amount)
            put(ReaderContract.Ingredients.COL_PRODUCT, product)
            put(ReaderContract.Ingredients.COL_MEASURE, measure.toString())
        }
        return db.insert(ReaderContract.Ingredients.TABLE_NAME, null, values)
    }

    fun updateIngredients(id: Long, recipeId: Int, amount: Int, product: String, measure: MeasureType): Int{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(ReaderContract.Ingredients.COL_RECIPE, recipeId)
            put(ReaderContract.Ingredients.COL_AMOUNT, amount)
            put(ReaderContract.Ingredients.COL_PRODUCT, product)
            put(ReaderContract.Ingredients.COL_MEASURE, measure.toString())
        }
        return db.update(ReaderContract.Ingredients.TABLE_NAME, values, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }


    private fun getDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }

}