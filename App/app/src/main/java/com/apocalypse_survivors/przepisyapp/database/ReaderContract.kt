package com.apocalypse_survivors.przepisyapp.database

import android.provider.BaseColumns

internal object ReaderContract {

    object Categories : BaseColumns {
        const val TABLE_NAME = "Categories"

        const val COL_NAME = "name"          // String      -> PK
        const val COL_DESCRIPTION = "desc"   // String      -> TEXT

        const val SQL_CREATE_CATEGORIES =
            "CREATE TABLE $TABLE_NAME (" +
                    "$COL_NAME TEXT PRIMARY KEY NOT NULL, " +
                    "$COL_DESCRIPTION TEXT)"

        const val SQL_DELETE_CATEGORIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Measures : BaseColumns {
        const val TABLE_NAME = "Measures"

        const val COL_NAME = "r_id"          // String  -> PK

        const val SQL_CREATE_MEASURES =
            "CREATE TABLE $TABLE_NAME ($COL_NAME TEXT PRIMARY KEY NOT NULL)"

        const val SQL_DELETE_MEASURES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Recipes : BaseColumns {
        const val TABLE_NAME = "Recipes"

        const val COL_NAME = "name"          // String      -> TEXT     NOT NULL
        const val COL_CATEGORY = "c_id"      // #Int        -> REFERENCES Categories.id
        const val COL_SUBCATEGORY = "sc_id"  // #Int        -> REFERENCES Categories.id
        const val COL_DESCRIPTION = "desc"   // String      -> TEXT
        const val COL_IMAGE = "image"        // String      -> TEXT
        const val COL_TIME = "time"          // Int         -> INTEGER              CHECK(time >= 0)
        const val COL_PORTION = "portion"    // Int         -> INTEGER              CHECK(portion >= 0)
        const val COL_CREATED = "created"    // DateTime    -> TEXT

        const val SQL_CREATE_RECIPES =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY NOT NULL, " +
                    "FOREIGN KEY($COL_CATEGORY) REFERENCES ${Categories.TABLE_NAME}(${Categories.COL_NAME}), " +
                    "FOREIGN KEY($COL_SUBCATEGORY) REFERENCES ${Categories.TABLE_NAME}(${Categories.COL_NAME}), " +
                    "$COL_NAME TEXT NOT NULL, " +
                    "$COL_DESCRIPTION TEXT, " +
                    "$COL_IMAGE TEXT, " +
                    "$COL_TIME INTEGER CHECK($COL_TIME >= 0), " +
                    "$COL_PORTION INTEGER CHECK($COL_PORTION >= 0), " +
                    "$COL_CREATED TEXT)"

        const val SQL_DELETE_RECIPES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Steps : BaseColumns {
        const val TABLE_NAME = "Steps"

        const val COL_RECIPE = "r_id"        // #Int    -> REFERENCES Recipes.id
        const val COL_NUMBER = "num"         // Int     -> INTEGER      NOT NULL    CHECK(num >= 0)
        const val COL_DESCRIPTION = "desc"   // String  -> TEXT

        const val SQL_CREATE_STEPS =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY NOT NULL, " +
                    "FOREIGN KEY($COL_RECIPE) REFERENCES ${Recipes.TABLE_NAME}(${BaseColumns._ID}), " +
                    "$COL_NUMBER INTEGER NOT NULL CHECK($COL_NUMBER >= 0), " +
                    "$COL_DESCRIPTION TEXT)"

        const val SQL_DELETE_STEPS = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    object Ingredients : BaseColumns {
        const val TABLE_NAME = "Ingredients"

        const val COL_RECIPE = "r_id"        // #Int    -> REFERENCES Recipes.id
        const val COL_AMOUNT = "amount"      // Int     -> REAL                     CHECK(amount >= 0)
        const val COL_PRODUCT = "product"    // String  -> TEXT
        const val COL_MEASURE  = "measure"   // #String -> REFERENCES Measures.name

        const val SQL_CREATE_INGREDIENTS =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                    "FOREIGN KEY($COL_RECIPE) REFERENCES ${Recipes.TABLE_NAME}(${BaseColumns._ID}), " +
                    "$COL_AMOUNT REAL CHECK($COL_AMOUNT >= 0), " +
                    "$COL_PRODUCT TEXT, " +
                    "FOREIGN KEY($COL_MEASURE) REFERENCES ${Measures.TABLE_NAME}(${Measures.COL_NAME}))"

        const val SQL_DELETE_INGREDIENTS = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}