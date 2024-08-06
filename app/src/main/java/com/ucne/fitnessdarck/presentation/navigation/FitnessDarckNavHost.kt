package com.ucne.fitnessdarck.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.ucne.fitnessdarck.Greeting
import com.ucne.fitnessdarck.presentation.screens.exercises.ExerciseScreen
import com.ucne.fitnessdarck.presentation.screens.settings.SettingsScreen

@Composable
fun FitnessDarckNavHost(
    currentRoute: String,
    currentRouteTrimmed: String,
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigation.entries
                    .forEachIndexed { _, navigationItem ->

                        val isSelected by remember(currentRoute) {
                            derivedStateOf { currentRouteTrimmed == navigationItem.route::class.qualifiedName }
                        }

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
                                navController.navigate(navigationItem.route)
                            }
                        )
                    }
            }
        }
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            navController = navController,
            startDestination = Screens.HomeGraph
        ) {
            navigation<Screens.HomeGraph>(
                startDestination = Screens.Home,
            ) {
                composable<Screens.Home> {
                    Greeting("Home")
                }

                composable<Screens.Routines> {
                    Greeting("Routines")
                }

                composable<Screens.Exercises> {
                    ExerciseScreen()
                }

                composable<Screens.Log> {
                    Greeting("Log")
                }

                composable<Screens.Settings> {
                    SettingsScreen()
                }
            }
        }
    }
}