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

    val tabs = listOf("home", "list", "settings")
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val selectedTabIndex = tabs.indexOf(currentRoute).coerceAtLeast(0)

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                tabs.forEachIndexed { index, route ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { navController.navigate(route) },
                        text = { Text(route) }
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
            composable("home") {
                HomeScreen(Modifier.padding(paddingValues))
            }
            composable("list") {
                DrinkListScreen(drinks, Modifier.padding(paddingValues))
            }
            composable("settings") {
                Settings(Modifier.padding(paddingValues))
            }
        }
    }
}

