package com.apocalypse_survivors.przepisyapp.database.Entities

import androidx.room.ColumnInfo
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
class IngredientEntity (
    @PrimaryKey
    val id : Int,

    @ColumnInfo(name = "recipe_id")
    val recipe_id : Int,

    @ColumnInfo(name = "measure_id")
    val measure_id : Int,

    @ColumnInfo(name = "amount")
    val amount : Double,

    @ColumnInfo(name = "product")
    val product : String
)