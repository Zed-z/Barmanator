package pl.poznan.put.barmanator.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun DrinkListItem(drink: Drink, onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(percent = 20))
            .clickable { onClick() }
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
                    Text(text = drink.category)
                }
            }
        }
    }
}

@Composable
fun DrinkList(drinks: List<Drink>, modifier: Modifier, onDrinkClick: (Long) -> Unit) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(drinks) { drink ->
            DrinkListItem(drink = drink, onClick = { onDrinkClick(drink.id) })
        }
    }
}

@Composable
fun DrinkListScreen(drinks: List<Drink>, modifier: Modifier = Modifier) {
    var selectedDrinkId by rememberSaveable { mutableStateOf<Long?>(null) }

    if (selectedDrinkId == null) {
        // Show list when no drink is selected
        DrinkList(drinks, onDrinkClick = { selectedDrinkId = it }, modifier = modifier)
    } else {
        // Show details when a drink is selected
        val drink = drinks.find { it.id == selectedDrinkId }
        if (drink != null) {
            DrinkDetail(
                modifier,
                drink,
                onBack = { selectedDrinkId = null } // Back button returns to list
            )
        } else {
            Text("Drink not found!", modifier.padding(16.dp))
        }
    }
}
