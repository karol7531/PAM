package com.apocalypse_survivors.przepisyapp.database.Entities

import androidx.room.ColumnInfo
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
    @PrimaryKey
    val id : Int,

    @ColumnInfo(name = "category_id")
    val category_id : Int,

    @ColumnInfo(name = "subcategory_id")
    val subcategory_id : Int,

    @ColumnInfo(name = "name")
    val name : String,

    @ColumnInfo(name = "description")
    val description : String?,

    @ColumnInfo(name = "image")
    val image : String?,

    @ColumnInfo(name = "time")
    val time : Int?,

    @ColumnInfo(name = "portion")
    val portion : Int?,

    @ColumnInfo(name = "created")
    val created : String?
)