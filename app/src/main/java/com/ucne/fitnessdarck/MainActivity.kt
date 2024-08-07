package com.ucne.fitnessdarck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ucne.fitnessdarck.presentation.navigation.FitnessDarckNavHost
import com.ucne.fitnessdarck.presentation.navigation.Screens
import com.ucne.fitnessdarck.presentation.screens.authentication.AuthState
import com.ucne.fitnessdarck.presentation.screens.authentication.AuthViewModel
import com.ucne.fitnessdarck.presentation.screens.settings.SettingsViewModel
import com.ucne.fitnessdarck.ui.theme.DataStoreManager
import com.ucne.fitnessdarck.ui.theme.FitnessDarckTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStoreManager = DataStoreManager(applicationContext)
        val settingsViewModel = SettingsViewModel(dataStoreManager)
        val authViewModel: AuthViewModel by viewModels()

        enableEdgeToEdge()

        setContent {
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState(initial = false)
            val authState by authViewModel.authState.observeAsState(AuthState.Unauthenticated)
            val isAuthenticated = authState is AuthState.Authenticated

            FitnessDarckTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()

                LaunchedEffect(authState) {
                    if (isAuthenticated) {
                        navController.navigate(Screens.HomeGraph::class.qualifiedName!!) {
                            popUpTo(0) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screens.Login::class.qualifiedName!!) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }

                FitnessDarckNavHost(
                    navController = navController,
                    isAuthenticated = isAuthenticated
                )
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