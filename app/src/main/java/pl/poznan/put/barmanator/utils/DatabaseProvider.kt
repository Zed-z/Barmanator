package pl.poznan.put.barmanator.utils

import androidx.compose.runtime.staticCompositionLocalOf
import pl.poznan.put.barmanator.data.Database

val LocalDatabase = staticCompositionLocalOf<Database> {
    error("MySingletonDependency not provided")
}