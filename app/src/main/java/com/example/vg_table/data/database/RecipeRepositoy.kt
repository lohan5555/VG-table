package com.example.vg_table.data.database

import com.example.vg_table.data.database.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [RecipeEntity] from a given data source.
 */
interface RecipeRepository {
    /**
     * Retrieve all the Recipe from the the given data source.
     */
    fun getAllRecipeStream(): Flow<List<RecipeEntity>>
    /**
     * Insert item in the data source
     */
    suspend fun insertRecipe(recipe: RecipeEntity)

    /**
     * Delete item from the data source
     */
    suspend fun deleteRecipe(recipe: RecipeEntity)
}
