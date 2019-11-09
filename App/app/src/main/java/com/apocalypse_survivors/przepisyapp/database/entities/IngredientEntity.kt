package com.apocalypse_survivors.przepisyapp.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Ingredients",
        foreignKeys = [
        ForeignKey(
            entity = RecipeEntity::class,
            parentColumns = ["id"],
            childColumns = ["recipe_id"]
        ),
        ForeignKey(
            entity = MeasureEntity::class,
            parentColumns = ["name"],
            childColumns = ["measure_id"]
        )])
data class IngredientEntity (

    val recipe_id : Int,

    val measure_id : Int,

    val amount : Double,

    val product : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}