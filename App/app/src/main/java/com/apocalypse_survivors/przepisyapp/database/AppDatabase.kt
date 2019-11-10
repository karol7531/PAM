package com.apocalypse_survivors.przepisyapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.apocalypse_survivors.przepisyapp.database.DAO.*
import com.apocalypse_survivors.przepisyapp.database.entities.*
import com.apocalypse_survivors.przepisyapp.database.DAO.CategoryType
import com.apocalypse_survivors.przepisyapp.database.DAO.MeasureType
import java.util.concurrent.Executors

//useful links:
// https://en.proft.me/2019/09/27/android-room-kotlin-coroutines-viewmodel-livedata/
@Database(entities = [CategoryEntity::class, IngredientEntity::class, MeasureEntity::class, RecipeEntity::class, StepEntity::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {

//    companion object : SingletonHolder<AppDatabase, Context>({
//        Room.databaseBuilder(it, AppDatabase::class.java, "RecipesDB")
//            .addCallback(object : Callback(){
//                override fun onCreate(db: SupportSQLiteDatabase) {
//                    super.onCreate(db)
//                }
//
//                fun populateCategories(){
//
//                }
//            }).build()
//    })

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "Sample.db")
                // prepopulate the database after onCreate was called
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // insert the data on the IO Thread
                        populateCategories()
                    }

                    fun populateCategories() {
                        Executors.newSingleThreadExecutor().execute {
                            getInstance(context).categoryDAO().insert(CategoriesData)
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