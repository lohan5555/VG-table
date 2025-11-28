package com.example.vg_table.data.models.searchRecipe

import com.example.vg_table.data.database.entities.RecipeEntity

data class Recipe(
    val analyzedInstructions: List<AnalyzedInstruction>,
    val id: Int,
    val image: String,
    val title: String,
)

data class RecipeResponse(
    val results: List<Recipe>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
)

fun Recipe.toEntity() = RecipeEntity(
    id = this.id,
    title = this.title,
    image = this.image
)