package com.ucne.fitnessdarck.presentation.screens.routines

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucne.fitnessdarck.data.local.entities.RoutineEntity
import com.ucne.fitnessdarck.presentation.screens.exercises.ExerciseItem
import com.ucne.fitnessdarck.presentation.screens.exercises.ExerciseViewModel

@Composable
fun RoutinesListScreen(
    routineViewModel: RoutineViewModel = hiltViewModel()
) {
    val routines by routineViewModel.allRoutines.collectAsState()
    var selectedRoutine by remember { mutableStateOf<RoutineEntity?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Routines:", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(routines) { routine ->
                RoutineCard(
                    routine = routine,
                    onRoutineClick = {
                        selectedRoutine = routine
                        showDialog = true
                    }
                )
            }
        }
    }

    selectedRoutine?.let { routine ->
        if (showDialog) {
            RoutineDetailDialog(
                routine = routine,
                onDismiss = { showDialog = false }
            )
        }
    }
}


@Composable
fun RoutineCard(
    routine: RoutineEntity,
    onRoutineClick: (RoutineEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onRoutineClick(routine) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Name: ${routine.name}", style = MaterialTheme.typography.headlineSmall)
            Text("Created by: ${routine.userId}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun RoutineDetailDialog(
    routine: RoutineEntity,
    exerciseViewModel: ExerciseViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val exercises by exerciseViewModel.getExercisesByIds(routine.exercises).collectAsState()

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
                Text("Routine: ${routine.name}", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Exercises:", style = MaterialTheme.typography.headlineSmall)
                LazyColumn {
                    items(exercises) { exercise ->
                        ExerciseItem(exercise = exercise, onClick = {})
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