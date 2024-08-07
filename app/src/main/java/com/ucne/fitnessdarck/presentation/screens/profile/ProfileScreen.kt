package com.ucne.fitnessdarck.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucne.fitnessdarck.data.local.entities.RoutineEntity
import com.ucne.fitnessdarck.presentation.screens.authentication.AuthState
import com.ucne.fitnessdarck.presentation.screens.authentication.AuthViewModel
import com.ucne.fitnessdarck.presentation.screens.routines.RoutineCard
import com.ucne.fitnessdarck.presentation.screens.routines.RoutineDetailDialog
import com.ucne.fitnessdarck.presentation.screens.routines.RoutineViewModel
@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel,
    routineViewModel: RoutineViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.observeAsState(AuthState.Unauthenticated)
    val routines by routineViewModel.allRoutines.collectAsState()

    // State variables
    var showDialog by remember { mutableStateOf(false) }
    var filteredRoutines by remember { mutableStateOf<List<RoutineEntity>>(emptyList()) }
    var selectedRoutine by remember { mutableStateOf<RoutineEntity?>(null) }

    // Get current user ID
    val currentUserId = when (authState) {
        is AuthState.Authenticated -> authViewModel.auth.currentUser?.uid
        else -> null
    }

    // LaunchedEffect to filter routines
    LaunchedEffect(routines, currentUserId) {
        filteredRoutines = if (currentUserId != null) {
            routines.filter { it.userId == currentUserId }
        } else {
            emptyList()
        }
        println("Filtered Routines: $filteredRoutines")  // Debug output
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )
        val email = when (authState) {
            is AuthState.Authenticated -> authViewModel.auth.currentUser?.email ?: "Unknown"
            else -> "Unknown"
        }
        Text(text = email, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = { showDialog = true }) {
            Text(text = "Show Routines")
        }
    }

    if (showDialog) {
        RoutineListDialog(
            routines = filteredRoutines,
            onDismiss = { showDialog = false },
            onRoutineClick = { routine ->
                selectedRoutine = routine
                showDialog = false
            }
        )
    }

    selectedRoutine?.let { routine ->
        RoutineDetailDialog(
            routine = routine,
            onDismiss = { selectedRoutine = null }
        )
    }
}

@Composable
fun RoutineListDialog(
    routines: List<RoutineEntity>,
    onDismiss: () -> Unit,
    onRoutineClick: (RoutineEntity) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Your Routines", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                if (routines.isEmpty()) {
                    Text("No routines available", style = MaterialTheme.typography.bodyLarge)
                } else {
                    LazyColumn {
                        items(routines) { routine ->
                            RoutineCard(
                                routine = routine,
                                onRoutineClick = { onRoutineClick(routine) }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    }
}