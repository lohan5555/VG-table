package com.example.vg_table.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    //viewModel: RecipeViewModel = viewModel(),
) {
    //val recipes = viewModel.recipes.value
    var search by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    colors = androidx.compose.material3.TextFieldDefaults.colors(
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
                        expanded = false
                        //viewModel.fetchRecipe(search)
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = false },
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
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
        ) {}
        ListeRecipe()
    }
}

@Composable
fun ListeRecipe(){

}

@Composable
fun Recipe(){

}

@Preview
@Composable
fun PreviewRecipe(){
    Recipe()
}