package com.example.vg_table.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.vg_table.viewModel.RecipeViewModel
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import com.example.vg_table.R
import com.example.vg_table.data.database.AppViewModelProvider
import com.example.vg_table.data.database.entities.RecipeBase
import com.example.vg_table.data.models.searchRecipe.toEntity

//Page permettant de faire une recherche de recette de cuisisne
// et affichant les résultats sous forme d'une liste
@Composable
fun SearchScreen(
    viewModel: RecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val recipes = viewModel.recipes.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value

    Column(modifier = Modifier.fillMaxWidth()) {
        Search(viewModel)
        if (isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if(error != null){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = error, color = MaterialTheme.colorScheme.onPrimary)
            }
        }

        ListRecipe(recipes, viewModel)
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
fun ListRecipe(listRecipe: List<RecipeBase>, viewModel: RecipeViewModel){
    LazyColumn(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        items(listRecipe){ recipe ->
            RecipeCard(recipe, viewModel)
        }
    }

}

//affichage d'une recette
@Composable
fun RecipeCard(recipe: RecipeBase, viewModel: RecipeViewModel) {
    //si la liste des ingrédients et des étapes est déroulée
    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
    ) {
        //Une box pour pouvoir superposer l'image et le bouton favoris
        Box{
            //la liste des recette enregistrées en favoris
            val favorites by viewModel.favorites.collectAsState()

            val isFavorite = favorites.any { it.id == recipe.id }

            AsyncImage(
                model = recipe.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(R.drawable.error),
                error = painterResource(R.drawable.error),
            )

            //bouton favoris
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
                        if(!isFavorite){
                            viewModel.addFavorite(recipe)
                        }else{
                            viewModel.removeFavorite(recipe.toEntity())
                        }
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
fun RecipeContent(recipe: RecipeBase) {
    val instructions = recipe.analyzedInstructions
    if (instructions.isNotEmpty()) {
        val steps = instructions[0].steps

        // Ingrédients
        steps.forEach { step ->
            step.ingredients.forEach { ingredient ->
                Text(" - ${ingredient.name}", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
        Text("\n")

        // Étapes
        steps.forEach { step ->
            Text(
                "- ${step.step}\n",
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Justify
            )
        }
    } else {
        Text("Aucune instruction disponible.", color = MaterialTheme.colorScheme.onPrimary)
    }
}