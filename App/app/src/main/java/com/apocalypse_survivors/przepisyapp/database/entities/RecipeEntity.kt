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
data class RecipeEntity (

    val category_id : String,

    val subcategory_id : String,

    val name : String,

    val description : String?,

    val image : String?,

    val time : Int?,

    val portion : Int?,
//TODO: give it actual time
    val created : String?
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}