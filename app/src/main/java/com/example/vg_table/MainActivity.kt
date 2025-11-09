package com.example.vg_table

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.vg_table.ui.theme.VGtableTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VGtableTheme {
                var selectedItem by remember { mutableIntStateOf(0) }
                val items = listOf("Saisons", "Recherche", "Favoris")
                val selectedIcons = listOf(Icons.Filled.DateRange, Icons.Filled.Search, Icons.Filled.Favorite)
                val unselectedIcons =
                    listOf(Icons.Outlined.DateRange, Icons.Outlined.Search, Icons.Outlined.Favorite)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color.Red
                        ) {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                                            contentDescription = item,
                                        )
                                    },
                                    label = { Text(item) },
                                    selected = selectedItem == index,
                                    onClick = { selectedItem = index },
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Text(
                        text = "Uwu",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}