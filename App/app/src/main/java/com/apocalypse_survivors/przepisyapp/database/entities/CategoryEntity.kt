package com.apocalypse_survivors.przepisyapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories")
data class CategoryEntity (
    @PrimaryKey
    val name: String,

    val description: String?
    )