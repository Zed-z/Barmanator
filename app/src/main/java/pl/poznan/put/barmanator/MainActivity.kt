package pl.poznan.put.barmanator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import pl.poznan.put.barmanator.data.Database
import pl.poznan.put.barmanator.data.Drink
import pl.poznan.put.barmanator.screens.HomeScreen
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import pl.poznan.put.barmanator.screens.DrinkListScreen
import pl.poznan.put.barmanator.utils.LiquidBg
import pl.poznan.put.barmanator.utils.LocalDatabase

class MainActivity : ComponentActivity() {

    private lateinit var database: Database

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        database = Database(this)

        //enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalDatabase provides database) {
                BarmanatorTheme {
                    MainScreen(database.getDrinks())
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val sampleDrinks = listOf(
        Drink(1, "Zombie", "Strong", "Shake well with ice", emptyList(), emptyList(), emptyList(), null),
        Drink(2, "Mojito", "Classic", "Muddle mint, add rum and soda", emptyList(), emptyList(), emptyList(), null),
        Drink(3, "Old Fashioned", "Smooth", "Stir whiskey with bitters", emptyList(), emptyList(), emptyList(), null)
    )

    BarmanatorTheme {
        MainScreen(drinks = sampleDrinks)
    }
}

data class TabItem(
    val title: String,
    val iconRes: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(drinks: List<Drink>) {
    val tabs = listOf(
        TabItem("Home", R.drawable.home),
        TabItem("All", R.drawable.all),
        TabItem("Ordinary", R.drawable.drink),
        TabItem("Shots", R.drawable.shot),
        TabItem("Cocktails", R.drawable.shaker),
        TabItem("Punches", R.drawable.punch),
        TabItem("Others", R.drawable.others)
    )
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)


    Box(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .zIndex(0f)
        )

        LiquidBg(
            waveColor = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier
                .fillMaxSize()
        )

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(Modifier.height(16.dp))
                    tabs.forEachIndexed { index, tab ->
                        val selected = pagerState.currentPage == index
                        NavigationDrawerItem(
                            label = { Text(
                                tab.title,
                                color = if (selected)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.primary
                            ) },
                            selected = selected,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    modifier = Modifier.size(48.dp),
                                    painter = painterResource(id = tab.iconRes),
                                    contentDescription = tab.title,
                                    tint = if (selected)
                                        MaterialTheme.colorScheme.onPrimary
                                    else
                                        MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                }
            }
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text(tabs[pagerState.currentPage].title) },
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                },
                bottomBar = {
                    ScrollableTabRow(
                        edgePadding = 0.dp,
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
                                        tint = MaterialTheme.colorScheme.primary
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
                        "Ordinary" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Ordinary") })

                        "Shots" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Shot") })

                        "Cocktails" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Cocktail") })

                        "Punches" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Punch") })

                        "Others" -> DrinkListScreen(
                            drinks,
                            Modifier,
                            filter = { it.category.contains("Other") })
                    }
                }
            }
        }
    }
}

