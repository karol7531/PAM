package com.apocalypse_survivors.przepisyapp.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Steps",
        foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipe_id"]
        )])
class StepEntity (

    @PrimaryKey
    val id : Int,

    @ColumnInfo(name = "recipe_id")
    val rec_id : Int,

    @ColumnInfo(name = "number")
    val number : Int,

    @ColumnInfo(name = "description")
    val description : String?
)