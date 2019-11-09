package com.apocalypse_survivors.przepisyapp.database.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Measures")
class MeasureEntity (
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String
)