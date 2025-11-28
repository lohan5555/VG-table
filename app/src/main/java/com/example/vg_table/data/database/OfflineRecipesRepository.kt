package com.example.vg_table.data.database

import com.example.vg_table.data.database.entities.RecipeEntity
import kotlinx.coroutines.flow.Flow

class OfflineRecipesRepository(private val recipeDao: RecipeDAO) : RecipeRepository {
    override fun getAllRecipeStream(): Flow<List<RecipeEntity>> = recipeDao.getAllRecipes()

    override suspend fun insertRecipe(item: RecipeEntity) = recipeDao.insert(item)

    override suspend fun deleteRecipe(item: RecipeEntity) = recipeDao.delete(item)
}
