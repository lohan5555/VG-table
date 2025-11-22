package com.example.vg_table.ui.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vg_table.data.models.searchRecipe.Recipe
import com.example.vg_table.viewModel.RecipeViewModel
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage

//Page permettant de faire une recherche de recette de cuisisne et affichant les
//résultats sous forme d'une liste
@Composable
fun SearchScreen(
    viewModel: RecipeViewModel = viewModel(),
) {
    val recipes = viewModel.recipes.value

    Column(modifier = Modifier.fillMaxWidth()) {
        Search(viewModel)
        ListRecipe(recipes)
    }
}

//barre de recherche
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(viewModel: RecipeViewModel){
    var search by rememberSaveable { mutableStateOf("") }

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                colors = TextFieldDefaults.colors(
                    focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onPrimary,
                ),
                query = search,
                onQueryChange = {
                    search = it
                },
                onSearch = {
                    viewModel.searchRecipe(search)
                },
                expanded = false,
                onExpandedChange = { null },
                placeholder = { Text("Looking for an recipe ...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "search")
                },
                trailingIcon = {
                    if (search.isNotEmpty()) {
                        IconButton(onClick = { search = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "clear search")
                        }
                    }
                }
            )
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ),
        windowInsets = WindowInsets(top = 0.dp), //pour coller la searchBar en haut
        expanded = false,
        onExpandedChange = { null },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
    ) {}
}

//affichage d'une liste de recette
@Composable
fun ListRecipe(listRecipe: List<Recipe>){
    LazyColumn(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        items(listRecipe){ recipe ->
            Recipe(recipe)
        }
    }

}

//affichage d'une recette
@Composable
fun Recipe(recipe: Recipe) {
    //si la liste des ingrédients et des étapes est déroulée
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
    ) {
        //Une box pour pouvoir superposer l'image et le bouton favoris
        Box{
            var isFavorite by rememberSaveable { mutableStateOf(false) }
            AsyncImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            Icon(
                imageVector = if (isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Favorite icone",
                modifier = Modifier
                    .clickable{
                        Log.d("Favorite icone", "Add au favoris")
                        isFavorite = !isFavorite
                    }
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(vertical = 8.dp, horizontal = 5.dp),
        ) {
            //Titre de la recette
            Text(
                recipe.title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 0.dp)
            )


            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier.padding(start = 10.dp, bottom = 50.dp)
            ) {
                RecipeContent(recipe)
            }
        }
    }
}

//les ingrédients et les étapes d'une recette
@Composable
fun RecipeContent(recipe: Recipe) {
    val instructions = recipe.analyzedInstructions
    if (instructions.isNotEmpty()) {
        val steps = instructions[0].steps

        // Ingrédients
        steps.forEach { step ->
            step.ingredients.forEach { ingredient ->
                Text(" - ${ingredient.name}", color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        // Étapes
        steps.forEach { step ->
            Text(
                step.step,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Justify
            )
        }
    } else {
        Text("Aucune instruction disponible.", color = MaterialTheme.colorScheme.onPrimary)
    }
}