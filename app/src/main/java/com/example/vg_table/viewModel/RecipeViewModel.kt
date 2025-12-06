package com.example.vg_table.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error


    //requete d'une recette à l'API spoonacular
    fun searchRecipe(search: String){
        _isLoading.value = true
        _error.value = null
        _recipes.value = emptyList()

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.apispoonacular.searchRecipe(query = search)
                _recipes.value = response.results
            }catch (e: Exception){
                if (e is java.net.UnknownHostException || e is java.net.ConnectException) {
                    _error.value = "No internet connection"
                } else {
                    _error.value = "An error has occurred"
                }
            }finally {
                _isLoading.value = false
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