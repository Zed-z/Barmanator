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
    primary = purple4,
    onPrimary = white,

    secondary = dark2,
    onSecondary = white,

    primaryContainer = dark1,
    onPrimaryContainer = white,
    secondaryContainer = dark1,
    onSecondaryContainer = white,

    tertiary = black,
    onTertiary = white,

    background = dark2,
    surface = dark2,
)

private val LightColorScheme = lightColorScheme(
    primary = pink3,
    onPrimary = white,

    secondary = pink2,
    onSecondary = black,

    primaryContainer = pink2,
    onPrimaryContainer = black,
    secondaryContainer = pink3,
    onSecondaryContainer = black,

    tertiary = black,
    onTertiary = white,

    background = pink1,
    surface = pink1,
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
        else -> LightColorScheme
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