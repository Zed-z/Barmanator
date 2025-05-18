package pl.poznan.put.barmanator.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB72929),
    secondary = Color(0xFF871616),
    tertiary = Color(0xFF859fC9),

    primaryContainer = Color(0xFF871616),
    secondaryContainer = Color(0xFF520909),

    background = Color(0xFF101020),
    surface = Color(0xFF202030),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2d1c7f),
    secondary = Color(0xFF0d0e20),
    tertiary = Color(0xFF7546e8),

    primaryContainer = Color(0xFF7546e8),
    secondaryContainer = Color(0xFF7546e8),

    background = Color(0xFFB0A9E5),
    surface = Color(0xFFC8B3F6),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

private val LightColrtSchemeAlt = lightColorScheme(
    primary = Color(0xFF1f0822),
    secondary = Color(0xFFD180c8),
    tertiary = Color( 0xFFF9ce75),

    primaryContainer = Color(0xFFE77665),
    secondaryContainer = Color(0xFFD180c8),

    background = Color(0xFFF9ce75),
    surface = Color(0xFF814881),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),


)

private val LightColrtSchemeAlt2 = lightColorScheme(
    primary = Color(0xFF373459),
    secondary = Color(0xFFc8b69d),
    tertiary = Color(0xFFc8b69d),

    primaryContainer = Color(0xFF572216),
    secondaryContainer = Color(0xFF763020),

    background = Color(0xFFc8b69d),
    surface = Color(0xFF927e64),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),


    )


@Composable
fun BarmanatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColrtSchemeAlt2
    }


    val darkTheme = isSystemInDarkTheme()
    val view = LocalView.current
    val window = (view.context as Activity).window

    // Allow Compose to draw behind system bars
    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    // System bar theme appropriate icons
    val systemUiController = rememberSystemUiController()
    if (darkTheme) {
        systemUiController.setSystemBarsColor(Color.Transparent)
    } else {
        systemUiController.setSystemBarsColor(Color.White)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}