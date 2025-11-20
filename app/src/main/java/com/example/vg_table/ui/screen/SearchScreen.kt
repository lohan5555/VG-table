package com.example.vg_table.ui.screen

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
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
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.vg_table.R

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(viewModel: RecipeViewModel){
    var search by remember { mutableStateOf("") }

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
    Column(
        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
    ) {
        //image de la recette
        AsyncImage(
            model = recipe.image,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        //Titre de la recette
        Text(
            recipe.title,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        val instructions = recipe.analyzedInstructions
        if (instructions.isNotEmpty()) {
            val steps = instructions[0].steps

            //Liste des ingrédients
            steps.forEach { step ->
                step.ingredients.forEach { ingredient ->
                    Text(" - ${ingredient.name}", color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            //Liste des étapes
            steps.forEach { step ->
                Text(step.step, color = MaterialTheme.colorScheme.onPrimary)
            }
        } else {
            Text(stringResource(R.string.noInstruction), color = MaterialTheme.colorScheme.onPrimary)
        }

    }
}
