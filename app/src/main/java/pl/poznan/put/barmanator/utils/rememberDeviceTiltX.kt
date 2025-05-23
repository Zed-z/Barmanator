package pl.poznan.put.barmanator.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember

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
