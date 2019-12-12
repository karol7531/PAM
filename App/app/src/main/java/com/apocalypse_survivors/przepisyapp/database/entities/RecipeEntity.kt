package com.apocalypse_survivors.przepisyapp.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Recipes",
        foreignKeys = [
            ForeignKey(
                entity = CategoryEntity::class,
                parentColumns = ["name"],
                childColumns = ["category_id"]
            ),
            ForeignKey(
                entity = CategoryEntity::class,
                parentColumns = ["name"],
                childColumns = ["subcategory_id"]
            )
        ])
class RecipeEntity (

    var category_id : String,

    var subcategory_id : String,

    var name : String,

    var description : String,

    var image : String,

    var time : Int,

    var portion : Int,

    var created : String
){

//    constructor() : this("UNSPECIFIED", "", "", "", "", 0, 0, "1970-01-01 00:00:00")

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

//    var category_id : String = "UNSPECIFIED"
//
//    var subcategory_id : String = "UNSPECIFIED"
//
//    var name : String = ""
//
//    var description : String = ""
//
//    var image : String= ""
//
//    var time : Int = 0
//
//    var portion : Int = 0
//
//    var created : String = "1970-01-01 00:00:00"
}