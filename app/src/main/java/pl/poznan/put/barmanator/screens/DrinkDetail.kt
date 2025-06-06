package pl.poznan.put.barmanator.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import pl.poznan.put.barmanator.data.Drink
import pl.poznan.put.barmanator.utils.Timer
import pl.poznan.put.barmanator.utils.TimerViewModel
import pl.poznan.put.barmanator.R
import pl.poznan.put.barmanator.utils.SmsButton
import pl.poznan.put.barmanator.utils.StrokeText
import kotlin.math.max
import kotlin.math.min


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    drink: Drink,
    isTablet: Boolean,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    toolbarHeightMin: Dp = 64.dp,
    toolbarHeightMax: Dp = 250.dp
) {
    val collapse = (1f - scrollBehavior.state.collapsedFraction).coerceIn(0f, 1f)
    val toolbarHeight = toolbarHeightMin + (toolbarHeightMax - toolbarHeightMin) * collapse

    println("Toolbar height: $toolbarHeight")

    Box {
        Box(
            modifier = Modifier
                .height(toolbarHeight)
                .fillMaxWidth()
        ) {
            if (drink.image != null) {
                AsyncImage(
                    model = drink.image,
                    placeholder = painterResource(R.drawable.hourglass),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(toolbarHeight),
                    contentScale = ContentScale.Crop
                )
            }
        }

        LargeTopAppBar(
            collapsedHeight = toolbarHeightMin,
            expandedHeight = toolbarHeightMax,
            scrollBehavior = scrollBehavior,
            title = {
                Column {
                    StrokeText(
                        content = drink.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold
                        ),
                        colorFront = MaterialTheme.colorScheme.onTertiary,
                        colorBack = MaterialTheme.colorScheme.tertiary
                    )
                    StrokeText(
                        content = drink.category,
                        style = MaterialTheme.typography.bodyLarge,
                        colorFront = MaterialTheme.colorScheme.onTertiary,
                        colorBack = MaterialTheme.colorScheme.tertiary
                    )
                }
            },
            navigationIcon = {
                if (!isTablet) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.largeTopAppBarColors(
                containerColor = Color.Transparent,
                scrolledContainerColor = MaterialTheme.colorScheme.surface
            ),
            modifier = Modifier
                .background(Color.Transparent)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkDetail(
    modifier: Modifier = Modifier,
    drink: Drink,
    onBack: () -> Unit,
    isTablet: Boolean
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val nestedScrollConnection = scrollBehavior.nestedScrollConnection

    Scaffold(
        modifier = modifier
            .fillMaxWidth()
            .nestedScroll(nestedScrollConnection),
        topBar = {
            TopAppBar(
                drink = drink,
                scrollBehavior = scrollBehavior,
                isTablet = isTablet,
                onBack = onBack
            )
        },
        floatingActionButton = {
            Row {
                Timer(
                    viewModel = viewModel<TimerViewModel>(
                        viewModelStoreOwner = LocalActivity.current as ViewModelStoreOwner,
                        key = "timer-${drink.id}",
                        initializer = {
                            TimerViewModel(1 * 60)
                        })
                )
                Spacer(modifier = Modifier.width(48.dp))
                SmsButton(
                    message = drink.ingredientsMeasutes.joinToString(separator = "\n") { (i, m) ->
                        "$i ($m)"
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Instructions", style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(text = drink.instructions)
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Ingredients", style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(2f)
                ) {
                    Text(text = drink.ingredients.joinToString(separator = "\n") { "- $it" })
                }
                Column {
                    Text(
                        text = drink.measures.joinToString(separator = "\n") { "($it)" },
                        textAlign = TextAlign.End
                    )
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}