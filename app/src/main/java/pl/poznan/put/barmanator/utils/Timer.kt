package pl.poznan.put.barmanator.utils

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay
import pl.poznan.put.barmanator.R
import kotlin.math.min
import kotlin.math.max
import kotlin.time.Duration.Companion.seconds

fun timeString(seconds: Int): String {
    return "${(seconds / 60).toString().padStart(2, '0')}:${(seconds % 60).toString().padStart(2, '0')}"
}

@Composable
fun TimerTimePicker(
    modifier: Modifier = Modifier,
    time: Int,
    hide: () -> Unit,
    callback: (Int) -> Unit
) {

    var timePickerTime by rememberSaveable { mutableIntStateOf(time) }

    AlertDialog(
        onDismissRequest = {

        },
        dismissButton = {
            Button(onClick = {
                hide()
            }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            Button(onClick = {
                hide()
                callback(timePickerTime)
            }) {
                Text("OK")
            }
        },
        title = {
            Text(
                text = "Choose timer length:",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,

                ) {
                    Button(
                        onClick = { timePickerTime = max(15, timePickerTime - 15) }
                    ) {
                        Text(text = "<")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = timeString(timePickerTime),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { timePickerTime = min(10 * 60, timePickerTime + 15) }
                    ) {
                        Text(text = ">")
                    }
                }
            }
        }
    )
}

@Composable
fun Timer(
    modifier: Modifier = Modifier,
    viewModel: TimerViewModel
) {
    var initialTime: State<Int> = viewModel.initialTime.collectAsState()
    var time: State<Int> = viewModel.time.collectAsState()
    var running: State<Boolean> = viewModel.running.collectAsState()

    var timePickerVisible by rememberSaveable() { mutableStateOf(false) }

    LaunchedEffect(running.value) {
        if (running.value) {
            while (time.value > 0) {
                delay(1.seconds)
                val current = time.value
                if (current > 0) {
                    viewModel.setTime(current - 1)
                }
            }
            viewModel.setRunning(false)
        }
    }

    if (timePickerVisible) {
        TimerTimePicker(
            time = initialTime.value,
            hide = {
                timePickerVisible = false
            },
            callback = fun (time: Int) {
                viewModel.setTime(time, true)
            }
        )
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FloatingActionButton(
            onClick = {
                timePickerVisible = true
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.hourglass),
                contentDescription = "Set Time"
            )
        }

        ExtendedFloatingActionButton(
            onClick = {
                if (!running.value) {
                    if (time.value == 0) {
                        viewModel.resetTime()
                    }
                    viewModel.setRunning(true)
                } else {
                    viewModel.setRunning(false)
                }
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.primaryContainer,
            icon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = if (running.value)
                        painterResource(id = R.drawable.media_pause)
                    else painterResource(id = R.drawable.media_play),
                    contentDescription = if (running.value)
                        "Pause"
                    else "Play"
                )
            },
            text = {
                Text(
                    text = timeString(time.value),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        )

        FloatingActionButton(
            onClick = {
                viewModel.resetTime()
                viewModel.setRunning(false)
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.media_stop),
                contentDescription = "Stop"
            )
        }
    }
}