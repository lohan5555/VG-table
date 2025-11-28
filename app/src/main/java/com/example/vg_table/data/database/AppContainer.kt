package com.example.vg_table.data.database

import android.content.Context

interface AppContainer {
    val itemsRepository: RecipeRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val itemsRepository: RecipeRepository by lazy {
        OfflineRecipesRepository(RecipeDataBase.getDatabase(context).recipeDao())
    }
}