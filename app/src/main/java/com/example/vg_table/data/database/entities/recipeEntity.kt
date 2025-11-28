package com.example.vg_table.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipeBDD")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val title: String,
    //val analyzedInstructions //on verra ça plus tard
)