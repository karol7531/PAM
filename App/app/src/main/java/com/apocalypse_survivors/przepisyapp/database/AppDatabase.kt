package com.apocalypse_survivors.przepisyapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apocalypse_survivors.przepisyapp.database.DAO.*
import com.apocalypse_survivors.przepisyapp.database.entities.*
import java.util.concurrent.Executors

//NOTE: useful links:
// https://en.proft.me/2019/09/27/android-room-kotlin-coroutines-viewmodel-livedata/
@Database(entities = [CategoryEntity::class, IngredientEntity::class, MeasureEntity::class, RecipeEntity::class, StepEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        // helps keep this class as singleton
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "Sample.db")
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        populateCategories()
//                        populateMeasures()
                    }

                    fun populateCategories() {
                        Executors.newSingleThreadExecutor().execute {
                            getInstance(context).categoryDAO().insert(CategoriesData)
                            Log.d("AppDatabase", "Categories inserted")
                        }
                    }

                    fun populateMeasures() {
                        Executors.newSingleThreadExecutor().execute{
                            getInstance(context).measureDAO().insert(MeasuresData)
                        }
                    }
                })
                .build()

        val CategoriesData = CategoryType.values()
            .filter { it.isMainCategory }
            .map { CategoryEntity(it.name, "") }


        val MeasuresData = MeasureType.values()
            .map { MeasureEntity(it.name) }
    }

    abstract fun categoryDAO() : CategoryDAO

    abstract fun ingredientDAO() : IngredientDAO

    abstract fun measureDAO() : MeasureDAO

    abstract fun recipeDAO() : RecipeDAO

    abstract fun stepDAO() : StepDAO
}