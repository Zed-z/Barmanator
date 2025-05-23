package pl.poznan.put.barmanator.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pl.poznan.put.barmanator.R
import pl.poznan.put.barmanator.utils.rememberDeviceTiltX
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val tiltX = rememberDeviceTiltX(context)
    val glassOffsetY = remember { Animatable(-1000f) }
    val drinkOffsetY = remember { Animatable(-1000f) }

    // Animation
    LaunchedEffect(Unit) {
        glassOffsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 800,
                easing = EaseOutBounce
            )
        )
        drinkOffsetY.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 400,
                easing = EaseOutBounce
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                Image(
                    modifier = Modifier
                        .size(500.dp)
                        .graphicsLayer {
                            rotationZ = tiltX * 45f
                            translationY = glassOffsetY.value
                        },
                    contentDescription = "App Icon",
                    painter = painterResource(id = R.drawable.icon_nodrink)
                )
                Image(
                    modifier = Modifier
                        .size(500.dp)
                        .graphicsLayer {
                            rotationZ = tiltX * 45f
                            translationY = drinkOffsetY.value
                        },
                    contentDescription = "App Icon",
                    painter = painterResource(id = R.drawable.icon_drink)
                )
            }
            Text(
                text = "Welcome to Barmanator!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "Made by: 156080, 156022",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}