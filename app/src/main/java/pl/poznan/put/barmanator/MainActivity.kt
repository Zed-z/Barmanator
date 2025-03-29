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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import pl.poznan.put.barmanator.data.Database
import pl.poznan.put.barmanator.data.Drink

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
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Favorites", "Tropical", "Strong")

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        topBar = {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
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
        ItemList(
            drinks = when (selectedTabIndex) {
                0 -> drinks
                1 -> drinks.filter { it.name.contains("fav", true) }  // Replace with real filtering logic
                2 -> drinks.filter { it.tagline.contains("Tropical", true) }
                3 -> drinks.filter { it.tagline.contains("Strong", true) }
                else -> drinks
            },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ListItem(drink: Drink) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(percent = 20))
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = drink.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Text(text = drink.tagline)
                }
            }
        }
    }
}

@Composable
fun ItemList(drinks: List<Drink>, modifier: Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(drinks) { drink ->
            ListItem(drink = drink)
        }
    }
}
