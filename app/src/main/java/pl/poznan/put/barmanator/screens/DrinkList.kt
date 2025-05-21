package pl.poznan.put.barmanator.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.poznan.put.barmanator.data.Drink
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat.recreate
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.poznan.put.barmanator.R
import pl.poznan.put.barmanator.ui.theme.BarmanatorTheme
import pl.poznan.put.barmanator.utils.LocalDatabase
import pl.poznan.put.barmanator.utils.StrokeText


@Composable
fun DrinkListItem(drink: Drink, onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .height(200.dp)
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(percent = 20))
            .clickable { onClick() }
    ) {
        if (drink.image != null) {


            AsyncImage(
                model = drink.image,
                contentDescription = null,
                placeholder =  painterResource(R.drawable.hourglass),
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        StrokeText(
                            content = drink.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                            colorFront = MaterialTheme.colorScheme.onTertiary,
                            colorBack = MaterialTheme.colorScheme.tertiary
                        )
                        StrokeText(content = drink.category,
                            style = MaterialTheme.typography.titleMedium.copy(),
                            colorFront = MaterialTheme.colorScheme.onTertiary,
                            colorBack = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrinkListScreenPreview() {
    val sampleDrinks = listOf(
        Drink(1, "Zombie", "Strong", "Shake well with ice", emptyList(), emptyList(), emptyList(), null),
        Drink(2, "Mojito", "Classic", "Muddle mint, add rum and soda", emptyList(), emptyList(), emptyList(), null),
        Drink(3, "Old Fashioned", "Smooth", "Stir whiskey with bitters", emptyList(), emptyList(), emptyList(), null)
    )

    BarmanatorTheme {
        DrinkListScreen(drinks = sampleDrinks)
    }
}

@Composable
fun DrinkListScreen(drinks: List<Drink>, modifier: Modifier = Modifier, filter: (Drink) -> Boolean = { true }) {

    var query by remember {mutableStateOf("")}
    var dat = LocalDatabase.current
    var refreshTrigger by remember {  mutableIntStateOf(0) }
    var actualDrinks  = remember(refreshTrigger)  {mutableStateOf(dat.getDrinks()) }

    val configuration  = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    // TODO: make this persistent when changing tabs
    var selectedDrinkHistory by rememberSaveable { mutableStateOf(emptyList<Long>()) }
    val selectedDrinkId = selectedDrinkHistory.lastOrNull()

    // Intercept back press
    BackHandler(enabled = (
            if (isTablet) (selectedDrinkHistory.size > 1)
            else (!selectedDrinkHistory.isEmpty())
            )
    ) {
        if (isTablet) {
            selectedDrinkHistory = selectedDrinkHistory.dropLast(1) // Go back an item
        } else {
            selectedDrinkHistory = listOf() // Go back completely
        }
    }

    // Reacts to orientation change and sets a default
    LaunchedEffect(isTablet) {
        if (isTablet && selectedDrinkHistory.isEmpty() && drinks.isNotEmpty()) {
            selectedDrinkHistory = listOf(drinks.first().id)
        }
    }

    Scaffold(
        modifier.fillMaxSize(),
        floatingActionButton = {
            if (isTablet || selectedDrinkId == null) {
                Box(
                    modifier = Modifier
                        .padding(start=32.dp, end=16.dp)
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = query,
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        shape = RoundedCornerShape(32.dp),
                        placeholder = {
                            Text(
                                text = "Filter / Search...",
                            )
                        },
                        onValueChange = { query = it },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        trailingIcon = {
                            if (query.isNotEmpty()) {
                                Button(
                                    //modifier = Modifier.weight(1f).fillMaxHeight(),
                                    onClick = {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            try {
                                                dat.SearchDrink(query)
                                            } catch (e: Exception) {
                                                println("Exception searching for drink $query")
                                            } finally {
                                                // to się wykona zawsze, na końcu
                                                refreshTrigger++
                                            }
                                        }
                                    }) {
                                    Icon(
                                        modifier = Modifier.size(48.dp).fillMaxHeight(),
                                        painter = painterResource(id = R.drawable.websearch),
                                        contentDescription = "Search"
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { contentPadding ->

        Row(
            modifier = Modifier.padding(contentPadding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier.weight(1f),
                content = {
                    val drinksFiltered = actualDrinks.value.filter { filter(it) }
                        .filter { it.name.lowercase().contains(query.lowercase()) }
                    items(drinksFiltered.size) { drinkId ->
                        val drink = drinksFiltered[drinkId]
                        DrinkListItem(
                            drink = drink,
                            onClick = {
                                selectedDrinkHistory = selectedDrinkHistory + drink.id
                            }
                        )
                    }
                }
            )

            if (isTablet) {
                selectedDrinkId?.let { id ->
                    val drink = drinks.find { it.id == id }
                    drink?.let {
                        DrinkDetail(
                            isTablet = isTablet,
                            modifier = Modifier.weight(3f),
                            drink = it,
                            onBack = {
                                if (selectedDrinkHistory.size > 1) {
                                    selectedDrinkHistory = selectedDrinkHistory.dropLast(1)
                                }
                            }
                        )
                    }
                }
            } else {
                if (selectedDrinkId != null) {
                    val drink = drinks.find { it.id == selectedDrinkId }
                    drink?.let {
                        DrinkDetail(
                            isTablet = isTablet,
                            modifier = modifier,
                            drink = it,
                            onBack = {
                                selectedDrinkHistory = listOf()
                            }
                        )
                    }
                }
            }
        }
    }
}
