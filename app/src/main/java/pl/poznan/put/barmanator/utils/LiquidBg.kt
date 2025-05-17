package pl.poznan.put.barmanator.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin
import android.view.animation.OvershootInterpolator

@Composable
fun rememberDeviceTiltX(context: Context): Float {
    val tilt = remember { mutableFloatStateOf(0f) }
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val gravitySensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) }

    DisposableEffect(Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val x = event.values[0]
                tilt.floatValue = (x / 9.81f).coerceIn(-1f, 1f) // normalize to <-1, 1>
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, gravitySensor, SensorManager.SENSOR_DELAY_UI)
        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    return tilt.floatValue
}

@Composable
fun LiquidBg(
    modifier: Modifier = Modifier,
    waveColor: Color = Color(0xFF871616),
    amplitude: Float = 40f,
    waveSpeed: Int = 3000,
    tiltMultiplier: Float = 1f,
    baseHeightRatio: Float = 0.5f
) {
    val context = LocalContext.current
    val tiltX = rememberDeviceTiltX(context)

    val fillProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        fillProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 3000, easing = LinearEasing)
        )
    }

    val tiltSmooth = remember { Animatable(0f) }
    LaunchedEffect(tiltX) {
        tiltSmooth.animateTo(
            targetValue = tiltX,
            animationSpec = tween(durationMillis = 100, easing = Easing {
                OvershootInterpolator().getInterpolation(it)
            })
        )
    }

    // Animate wave scroll
    val offsetAnim = rememberInfiniteTransition(label = "wave_scroll")
    val waveOffset by offsetAnim.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(waveSpeed), repeatMode = RepeatMode.Restart),
        label = "wave_offset"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val baseY = height - (height * baseHeightRatio * fillProgress.value)
        val waveLength = width / 2
        val verticalTiltShift = tiltSmooth.value * tiltMultiplier

        val path = Path().apply {
            moveTo(0f, height)
            lineTo(0f, baseY)

            for (x in 0..width.toInt()) {
                val normalizedX = x + (waveOffset * waveLength)
                val y = baseY - amplitude * sin((normalizedX / waveLength) * 2 * PI).toFloat() + (x - width.toInt() / 2) * verticalTiltShift
                lineTo(x.toFloat(), y)
            }
            lineTo(width, height)
            close()
        }

        drawPath(path = path, color = waveColor)
    }
}
