package com.ucne.fitnessdarck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ucne.fitnessdarck.presentation.navigation.BottomNavigation
import com.ucne.fitnessdarck.presentation.navigation.FitnessDarckNavHost
import com.ucne.fitnessdarck.ui.theme.DataStoreManager
import com.ucne.fitnessdarck.ui.theme.FitnessDarckTheme
import com.ucne.fitnessdarck.presentation.screens.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager = DataStoreManager(applicationContext)
        val settingsViewModel = SettingsViewModel(dataStoreManager)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme = settingsViewModel.isDarkTheme.collectAsState().value
            FitnessDarckTheme(darkTheme = isDarkTheme) {

                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                
                val currentRoute = navBackStackEntry?.destination?.route
                    ?: BottomNavigation.HOME.route::class.qualifiedName.orEmpty()

                val currentRouteTrimmed by remember(currentRoute) {
                    derivedStateOf { currentRoute.substringBefore("?") }
                }

                FitnessDarckNavHost(currentRoute, currentRouteTrimmed, navController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}