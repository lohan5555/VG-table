package com.example.vg_table.data.api

import com.example.vg_table.BuildConfig
import com.example.vg_table.data.models.searchRecipe.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpoonacularService {

    @GET("recipes/complexSearch")
    suspend fun searchRecipe(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("query") query: String = "cake",
        @Query("addRecipeInformation") addRecipeInformation: String = "true",
        @Query("addRecipeInstructions") addRecipeInstructions: String = "true",
    ): RecipeResponse
}