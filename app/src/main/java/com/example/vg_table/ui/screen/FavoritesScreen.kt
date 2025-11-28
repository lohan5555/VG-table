package com.example.vg_table.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vg_table.R
import com.example.vg_table.data.database.AppViewModelProvider
import com.example.vg_table.viewModel.RecipeViewModel

@Composable fun FavorisScreen(
    viewModel: RecipeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val favorites by viewModel.favorites.collectAsState()
    if(!favorites.isNotEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.noFavorites), color = MaterialTheme.colorScheme.onPrimary)
        }
    }else{
        ListRecipe(listRecipe = favorites, viewModel = viewModel)
    }

}