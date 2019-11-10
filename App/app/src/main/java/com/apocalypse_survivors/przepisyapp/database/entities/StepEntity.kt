package com.apocalypse_survivors.przepisyapp.database.entities

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
data class StepEntity (

    val recipe_id : Int,

    val number : Int,

    val description : String?
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}