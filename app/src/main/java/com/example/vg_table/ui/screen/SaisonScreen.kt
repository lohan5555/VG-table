package com.example.vg_table.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vg_table.data.models.Product
import com.example.vg_table.viewModel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

//Page listant les fruits et legumes de saison dans une liste de card
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaisonsScreen(
    viewModel: ProductViewModel = viewModel(),
) {
    val products = viewModel.products.value

    if(products.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }else{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products){product ->
                ProductCard(product)
            }
        }
    }
}

//Composant représentant une card de fruit ou légume de saisons
@Composable
fun ProductCard(product: Product){
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .aspectRatio(1f), //pour des cards carrés
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ){
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            Text(
                text = product.name,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}