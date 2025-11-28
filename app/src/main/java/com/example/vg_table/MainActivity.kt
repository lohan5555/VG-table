package com.example.vg_table

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.SystemBarStyle
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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import com.example.vg_table.ui.screen.FavorisScreen
import com.example.vg_table.ui.screen.SaisonsScreen
import com.example.vg_table.ui.screen.SearchScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                scrim = 0xFFF5AE72.toInt(),
                darkScrim = 0xFFF5AE72.toInt()
            )
        )
        setContent {
            VGtableTheme {
                val navController = rememberNavController()

                val screens = listOf(
                    Screen.Search,
                    Screen.Seasons,
                    Screen.Favorites
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.secondary
                        ) {
                            val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                            screens.forEach { screen ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            if (currentDestination?.route == screen.route){
                                                screen.selectedIcon
                                            }else{
                                                screen.unselectedIcon
                                            },
                                            contentDescription = screen.label
                                        )
                                    },
                                    label = { Text(screen.label) },
                                    selected = currentDestination?.route == screen.route,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                                        unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                                        selectedTextColor = MaterialTheme.colorScheme.onSecondary,
                                        unselectedTextColor = MaterialTheme.colorScheme.onSecondary,
                                        indicatorColor = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Seasons.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Seasons.route) { SaisonsScreen() }
                        composable(Screen.Search.route) { SearchScreen() }
                        composable(Screen.Favorites.route) { FavorisScreen() }
                    }
                }
            }
        }
    }
}

//class Screen, définisant la route, le nom, le label et les icons d'un écran de l'app
sealed class Screen(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Seasons : Screen("seasons", "Seasons", Icons.Filled.DateRange, Icons.Outlined.DateRange)
    object Search : Screen("search", "Search", Icons.Filled.Search, Icons.Outlined.Search)
    object Favorites : Screen("favorites", "Favorites", Icons.Filled.FavoriteBorder, Icons.Outlined.FavoriteBorder)
}