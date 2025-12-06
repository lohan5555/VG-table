package com.example.vg_table.ui.screen

import com.example.vg_table.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vg_table.data.models.Month
import com.example.vg_table.data.models.Product
import com.example.vg_table.viewModel.ProductViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect

//Page listant les fruits et legumes de saison dans une liste de card
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaisonsScreen(
    viewModel: ProductViewModel = viewModel(),
) {
    val products = viewModel.products.value

    LaunchedEffect(Unit) {
        val month = SimpleDateFormat("MM", Locale.getDefault()).format(Date()).toInt()
        viewModel.fetchProducts(month.toString())
    }

    if(products.isEmpty()){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }else{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconApp()
            MonthSelect(viewModel)
            ProductList(products)
        }
    }
}

//affiche l'icon de l'application
@Composable
fun IconApp(){
    Image(
        modifier = Modifier
            .size(100.dp)
            .padding(top = 10.dp),
        painter = painterResource(id = R.drawable.icon),
        contentDescription = "iconApp"
    )
}

//ExposedDropdownMenu pour choisir le mois
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthSelect(
    viewModel: ProductViewModel,
){
    val itemList = listOf(
        Month(1,"January"),
        Month(2,"February"),
        Month(3,"March"),
        Month(4,"April"),
        Month(5,"May"),
        Month(6,"June"),
        Month(7,"July"),
        Month(8,"August"),
        Month(9,"September"),
        Month(10,"October"),
        Month(11,"November"),
        Month(12,"December")
    )
    var expanded by remember { mutableStateOf(false) }
    val currentMonthId = SimpleDateFormat("MM", Locale.getDefault()).format(Date()).toInt()
    var selectedItem by remember { mutableStateOf(Month(currentMonthId,itemList[currentMonthId-1].name)) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier
            .padding(20.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(10.dp))
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = selectedItem.name,
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = "Expand menu",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                },
                colors = androidx.compose.material3.TextFieldDefaults.colors(
                    //container
                    focusedContainerColor = MaterialTheme.colorScheme.secondary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                    //la ligne qui indique le focus
                    focusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.tertiary,
                    //le texte
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    //l'icon déroulante

                )
            )
        }

        //liste des options du menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.tertiary)
        ) {
            itemList.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(item.name, color = MaterialTheme.colorScheme.onPrimary)
                        }
                    },
                    onClick = {
                        selectedItem = itemList[index]
                        expanded = false
                        viewModel.fetchProducts(selectedItem.id.toString())
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

//affichage d'une liste de Product
@Composable
fun ProductList(products: List<Product>){
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