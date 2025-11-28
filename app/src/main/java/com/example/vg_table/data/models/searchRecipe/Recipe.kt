package com.example.vg_table.data.models.searchRecipe

import com.example.vg_table.data.database.entities.RecipeBase
import com.example.vg_table.data.database.entities.RecipeEntity

//une recette récupérer depuis l'API
data class Recipe(
    override val analyzedInstructions: List<AnalyzedInstruction>,
    override val id: Int,
    override val image: String,
    override val title: String,
): RecipeBase

data class RecipeResponse(
    val results: List<Recipe>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
)

//convertion pour persistence local
fun RecipeBase.toEntity() = RecipeEntity(
    id = this.id,
    title = this.title,
    image = this.image,
    analyzedInstructions = this.analyzedInstructions
)