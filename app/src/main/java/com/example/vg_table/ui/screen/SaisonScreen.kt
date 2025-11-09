package com.example.vg_table.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vg_table.data.models.Product
import com.example.vg_table.viewModel.ProductViewModel
import androidx.compose.foundation.lazy.items
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SaisonsScreen(
    viewModel: ProductViewModel = viewModel(),
    paddingValues: PaddingValues
) {
    val products = viewModel.products.value

    if(products.isEmpty()){
        CircularProgressIndicator()
    }else{
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding((paddingValues))
        ) {
            items(products){product ->
                ProductCard(product)
            }
        }
    }
}


@Composable
fun ProductCard(product: Product){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nom : ${product.name}")
        }
    }
}