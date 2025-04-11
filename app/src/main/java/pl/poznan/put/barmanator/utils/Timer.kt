package pl.poznan.put.barmanator.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import pl.poznan.put.barmanator.R
import kotlin.math.floor
import kotlin.time.Duration.Companion.seconds

fun timeString(seconds: Int): String {
    return "${(seconds / 60).toString().padStart(2, '0')}:${(seconds % 60).toString().padStart(2, '0')}"
}

@Composable
fun Timer(
    modifier: Modifier = Modifier,
    startValue: Int = 10 * 60
) {
    var time: Int by remember { mutableIntStateOf(startValue) }
    var running: Boolean by remember { mutableStateOf(false) }

    LaunchedEffect(running) {
        if (running) {
            while (time > 0) {
                delay(1.seconds)
                time--
            }
            running = false
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = {
                if (!running) {
                    running = true
                } else {
                    running = false
                }
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = if (running)
                    painterResource(id = R.drawable.media_pause)
                    else painterResource(id = R.drawable.media_play),
                contentDescription = if (running)
                    "Pause"
                    else "Play"
            )
            Text(
                text = timeString(time),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }

        Button(
            onClick = {
                time = startValue
                running = false
            }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.media_stop),
                contentDescription = "Stop"
            )
            Text(
                text = "",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
        }
    }
}