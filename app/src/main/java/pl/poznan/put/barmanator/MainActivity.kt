package pl.poznan.put.barmanator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.poznan.put.barmanator.ui.theme.BarmanatorTheme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pl.poznan.put.barmanator.data.Database
import pl.poznan.put.barmanator.data.Drink
import pl.poznan.put.barmanator.screens.DrinkList
import pl.poznan.put.barmanator.screens.HomeScreen
import pl.poznan.put.barmanator.screens.Settings
import androidx.navigation.compose.*
import pl.poznan.put.barmanator.screens.DrinkDetail

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
        Drink(1, "Zombie", "Tropical Beast", "Strong and fruity cocktail", "Shake well with ice", "Rum, pineapple juice, lime"),
        Drink(2, "Mojito", "Refreshing Mint", "Classic Cuban cocktail", "Muddle mint, add rum and soda", "Rum, mint, lime, soda"),
        Drink(3, "Old Fashioned", "Whiskey Classic", "Smooth and strong", "Stir whiskey with bitters", "Whiskey, sugar, bitters")
    )

    BarmanatorTheme {
        MainScreen(drinks = sampleDrinks)
    }
}

@Composable
fun MainScreen(drinks: List<Drink>) {
    val navController = rememberNavController()
    val tabs = listOf("home", "list", "settings") // Use navigation routes for tabs

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            ScrollableTabRow(
                selectedTabIndex = tabs.indexOf(currentRoute).takeIf { it >= 0 } ?: 0,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, route ->
                    Tab(
                        selected = currentRoute == route,
                        onClick = { navController.navigate(route) }, // Navigate instead of updating index
                        text = { Text(route.replaceFirstChar { it.uppercase() }) } // Capitalize first letter
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { println("Pressed!") }) {
                Text("Timer")
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home", // Make sure it starts at home
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(Modifier.padding(paddingValues))
            }
            composable("list") {
                DrinkList(
                    drinks,
                    onDrinkClick = { drinkId -> navController.navigate("detail/$drinkId") },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            composable("settings") {
                Settings(Modifier.padding(paddingValues))
            }
            composable("detail/{drinkId}") { backStackEntry ->
                val drinkId = backStackEntry.arguments?.getString("drinkId")?.toLongOrNull()
                val drink = drinks.find { it.id == drinkId }
                if (drink != null) {
                    DrinkDetail(Modifier.padding(paddingValues), drink)
                } else {
                    Text("Drink not found!")
                }
            }
        }
    }
}

