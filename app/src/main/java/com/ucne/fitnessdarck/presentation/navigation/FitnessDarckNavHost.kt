package com.ucne.fitnessdarck.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import com.ucne.fitnessdarck.presentation.screens.authentication.LoginScreen
import com.ucne.fitnessdarck.presentation.screens.authentication.SignUpScreen
import com.ucne.fitnessdarck.presentation.screens.exercises.ExerciseScreen
import com.ucne.fitnessdarck.presentation.screens.profile.ProfileScreen
import com.ucne.fitnessdarck.presentation.screens.routines.CreateRoutineScreen
import com.ucne.fitnessdarck.presentation.screens.routines.RoutinesListScreen
import com.ucne.fitnessdarck.presentation.screens.settings.SettingsScreen

@Composable
fun FitnessDarckNavHost(
    navController: NavHostController,
    isAuthenticated: Boolean
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = remember(currentRoute, isAuthenticated) {
        currentRoute != Screens.Login::class.qualifiedName &&
                currentRoute != Screens.Signup::class.qualifiedName &&
                isAuthenticated
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar {
                    BottomNavigation.entries.forEach { navigationItem ->
                        val isSelected = currentRoute == navigationItem.route::class.qualifiedName

                        NavigationBarItem(
                            selected = isSelected,
                            label = { Text(navigationItem.label) },
                            icon = {
                                Icon(
                                    navigationItem.icon,
                                    contentDescription = navigationItem.label
                                )
                            },
                            onClick = {
                                navController.navigate(navigationItem.route::class.qualifiedName!!) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = navController,
            startDestination = if (isAuthenticated) Screens.HomeGraph::class.qualifiedName!! else Screens.Login::class.qualifiedName!!
        ) {
            composable(Screens.Login::class.qualifiedName!!) {
                LoginScreen(navController)
            }
            composable(Screens.Signup::class.qualifiedName!!) {
                SignUpScreen(navController)
            }
            navigation(
                startDestination = Screens.Home::class.qualifiedName!!,
                route = Screens.HomeGraph::class.qualifiedName!!
            ) {
                composable(Screens.Home::class.qualifiedName!!) {
                    CreateRoutineScreen()
                }
                composable(Screens.Routines::class.qualifiedName!!) {
                    RoutinesListScreen()
                }
                composable(Screens.Exercises::class.qualifiedName!!) {
                    ExerciseScreen()
                }
                composable(Screens.Profile::class.qualifiedName!!) {
                    ProfileScreen(authViewModel = hiltViewModel())
                }
                composable(Screens.Settings::class.qualifiedName!!) {
                    SettingsScreen()
                }
            }
        }
    }
}