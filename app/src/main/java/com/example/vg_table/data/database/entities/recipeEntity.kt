package com.example.vg_table.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.vg_table.data.models.searchRecipe.AnalyzedInstruction

@Entity(tableName = "recipeBDD")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val image: String,
    override val title: String,
    override val analyzedInstructions: List<AnalyzedInstruction>
): RecipeBase

//interface générique afin de gérer les RecipeEntity et les Recipe de la même manière
interface RecipeBase {
    val id: Int
    val title: String
    val image: String
    val analyzedInstructions: List<AnalyzedInstruction>
}
