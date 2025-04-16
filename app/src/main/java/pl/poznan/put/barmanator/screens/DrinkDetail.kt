package pl.poznan.put.barmanator.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.poznan.put.barmanator.data.Drink
import pl.poznan.put.barmanator.utils.Timer
import pl.poznan.put.barmanator.utils.TimerViewModel

@Composable
fun DrinkDetail(modifier: Modifier = Modifier, drink: Drink, onBack: () -> Unit, isTablet: Boolean) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            Timer(
                viewModel = viewModel<TimerViewModel>(key = "timer-${drink.id}") {
                    TimerViewModel(1 * 60)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (!isTablet) {
                Button(onClick = onBack) {
                    Text("Back")
                }
            }
            Text(text = drink.name, style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold
            ))
            Text(text = drink.category, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = drink.instructions)
        }
    }
}