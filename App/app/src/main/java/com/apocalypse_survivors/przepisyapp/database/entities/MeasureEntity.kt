package com.apocalypse_survivors.przepisyapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Measures")
data class MeasureEntity (
    @PrimaryKey
    val name: String
)