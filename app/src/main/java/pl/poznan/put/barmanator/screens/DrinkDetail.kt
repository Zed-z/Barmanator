package pl.poznan.put.barmanator.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import pl.poznan.put.barmanator.data.Drink
import pl.poznan.put.barmanator.utils.Timer
import pl.poznan.put.barmanator.utils.TimerViewModel
import pl.poznan.put.barmanator.R
import pl.poznan.put.barmanator.utils.SmsButton

@Composable
fun DrinkDetail(modifier: Modifier = Modifier, drink: Drink, onBack: () -> Unit, isTablet: Boolean) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            Timer(
                viewModel = viewModel<TimerViewModel>(
                    viewModelStoreOwner = LocalActivity.current as ViewModelStoreOwner,
                    key = "timer-${drink.id}",
                    initializer = {
                    TimerViewModel(1 * 60)
                })
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
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
            if (drink.image != null) {
                Spacer(modifier = Modifier.height(32.dp))


                AsyncImage(
                    model = drink.image,
                    placeholder =  painterResource(R.drawable.hourglass),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Instructions", style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold
            ))
            Text(text = drink.instructions)
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Ingredients", style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold
            ))
            Row (
                modifier = Modifier.fillMaxSize()
            ) {
                Column (
                    modifier = Modifier.weight(2f)
                ) {
                    Text(text = drink.ingredients.joinToString(separator = "\n") { "- $it" })
                }
                Column {
                    Text(text = drink.measures.joinToString(separator = "\n") { "($it)" }, textAlign = TextAlign.End)
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            SmsButton(
                buttonText = "Send Ingredients via SMS...",
                message = drink.ingredientsMeasutes.joinToString(separator = "\n") {
                    (i, m) -> "$i ($m)"
                }
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}