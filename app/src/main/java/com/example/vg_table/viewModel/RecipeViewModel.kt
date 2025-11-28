package com.example.vg_table.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vg_table.data.api.RetrofitInstance
import com.example.vg_table.data.database.RecipeRepository
import com.example.vg_table.data.database.entities.RecipeBase
import com.example.vg_table.data.database.entities.RecipeEntity
import com.example.vg_table.data.models.searchRecipe.Recipe
import com.example.vg_table.data.models.searchRecipe.toEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeViewModel(
    private val repository: RecipeRepository
): ViewModel() {

    //la liste des recettes sur SearchScreen
    private val _recipes = mutableStateOf<List<Recipe>>(emptyList())
    val recipes: State<List<Recipe>> = _recipes

    //la liste des recettes en favoris sur FavoritesScreen
    //et pour afficher sur searchScreen celle qui sont déjà en favoris
    val favorites = repository.getAllRecipeStream()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    //requete d'une recette à l'API spoonacular
    fun searchRecipe(search: String){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apispoonacular.searchRecipe(query = search)
                _recipes.value = response.results
            }catch (e: Exception){
                Log.e("RecipeViewModel", "Erreur API", e)
            }
        }
    }

    // Ajouter aux favoris d'une recette
    fun addFavorite(recipe: RecipeBase) {
        viewModelScope.launch {
            repository.insertRecipe(recipe.toEntity())
        }
    }

    // Supprimer d'une recette des favoris
    fun removeFavorite(entity: RecipeEntity) {
        viewModelScope.launch {
            repository.deleteRecipe(entity)
        }
    }
}