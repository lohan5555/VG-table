package com.example.vg_table.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vg_table.data.api.RetrofitInstance
import com.example.vg_table.data.models.searchRecipe.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel: ViewModel() {
    private val _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes


    fun searchRecipe(search: String){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apispoonacular.searchRecipe(query = search)
                _recipes.value = response.results
                Log.d("RecipeViewModel", "Fetched ${response.results.size} recette")
                Log.d("RecipeViewModel", response.results[0].analyzedInstructions[0].steps[0].ingredients.toString())
            }catch (e: Exception){
                Log.e("RecipeViewModel", "Erreur API", e)
            }
        }
    }
}