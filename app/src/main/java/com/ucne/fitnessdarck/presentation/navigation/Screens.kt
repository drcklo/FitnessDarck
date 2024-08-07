package com.ucne.fitnessdarck.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Api
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed class Screens {
    @Serializable
    data object HomeGraph : Screens()
    @Serializable
    data object Home: Screens()
    @Serializable
    data object Routines : Screens()
    @Serializable
    data object Exercises : Screens()
    @Serializable
    data object Profile : Screens()
    @Serializable
    data object Settings : Screens()

    @Serializable
    data object Login : Screens()

    @Serializable
    data object Signup : Screens()

    @Serializable
    data object CreateRoutineScreen : Screens()

}
enum class BottomNavigation(val label: String, val icon: ImageVector, val route: Screens) {
    HOME("Home", Icons.Filled.Home, Screens.Home),
    ROUTINES("Routines", Icons.Filled.Api, Screens.Routines),
    EXERCISES("Exercises", Icons.Filled.AccessibilityNew, Screens.Exercises),
    PROFILE("Profile", Icons.Filled.Person, Screens.Profile),
    SETTINGS("Settings", Icons.Filled.Settings, Screens.Settings) ;
}