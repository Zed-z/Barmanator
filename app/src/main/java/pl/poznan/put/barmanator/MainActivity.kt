package pl.poznan.put.barmanator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import pl.poznan.put.barmanator.data.Database
import pl.poznan.put.barmanator.data.Drink
import pl.poznan.put.barmanator.screens.HomeScreen
import pl.poznan.put.barmanator.screens.Settings
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
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

data class TabItem(
    val title: String,
    val iconRes: Int
)

@Composable
fun MainScreen(drinks: List<Drink>) {
    val tabs = listOf(
        TabItem("Home", R.drawable.home),
        TabItem("All", R.drawable.drink),
        TabItem("Shots", R.drawable.drink),
        TabItem("Ordinary", R.drawable.drink),
        TabItem("Cocktails", R.drawable.drink),
        TabItem("Settings", R.drawable.home)
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        bottomBar = {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = { Text(tab.title) },
                        icon = {
                            Icon(
                                modifier = Modifier.size(48.dp),
                                painter = painterResource(id = tab.iconRes),
                                contentDescription = tab.title,
                                tint = Color.Unspecified
                            )
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) { page ->
            when (tabs[page].title) {
                "Home" -> HomeScreen(Modifier)
                "All" -> DrinkListScreen(drinks, Modifier)
                "Shots" -> DrinkListScreen(drinks, Modifier, filter = { it.category.contains("Shot") })
                "Ordinary" -> DrinkListScreen(drinks, Modifier, filter = { it.category.contains("Ordinary") })
                "Cocktails" -> DrinkListScreen(drinks, Modifier, filter = { it.category.contains("Cocktail") })
                "Settings" -> Settings(Modifier)
            }
        }
    }
}

