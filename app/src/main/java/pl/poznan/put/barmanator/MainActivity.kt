package pl.poznan.put.barmanator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.poznan.put.barmanator.ui.theme.BarmanatorTheme

import androidx.compose.material.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.ui.res.painterResource
import pl.poznan.put.barmanator.data.Database
import pl.poznan.put.barmanator.data.Drink
import pl.poznan.put.barmanator.screens.HomeScreen
import pl.poznan.put.barmanator.screens.Settings
import androidx.navigation.compose.*
import pl.poznan.put.barmanator.screens.DrinkListScreen

class MainActivity : ComponentActivity() {

    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        database = Database(this)

        //enableEdgeToEdge()
        setContent {
            BarmanatorTheme {
                MainScreen(database.getDrinks())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val sampleDrinks = listOf(
        Drink(1, "Zombie", "Strong", "Shake well with ice", emptyList(), emptyList(), null),
        Drink(2, "Mojito", "Classic", "Muddle mint, add rum and soda", emptyList(), emptyList(), null),
        Drink(3, "Old Fashioned", "Smooth", "Stir whiskey with bitters", emptyList(), emptyList(), null)
    )

    BarmanatorTheme {
        MainScreen(drinks = sampleDrinks)
    }
}

@Composable
fun MainScreen(drinks: List<Drink>) {
    val navController = rememberNavController()

    val tabs = listOf("Home", "List", "Settings")
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val selectedTabIndex = tabs.indexOf(currentRoute).coerceAtLeast(0)

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        bottomBar = {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                tabs.forEachIndexed { index, route ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { navController.navigate(route) },
                        text = { Text(route) },
                        icon = {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(id = R.drawable.media_pause),
                                contentDescription = route
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("Home") {
                HomeScreen(Modifier)
            }
            composable("List") {
                DrinkListScreen(drinks, Modifier)
            }
            composable("Settings") {
                Settings(Modifier)
            }
        }
    }
}

