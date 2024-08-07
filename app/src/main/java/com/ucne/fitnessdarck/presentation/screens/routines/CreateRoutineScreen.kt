package com.ucne.fitnessdarck.presentation.screens.routines

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucne.fitnessdarck.data.local.entities.ExerciseEntity
import com.ucne.fitnessdarck.presentation.screens.authentication.AuthState
import com.ucne.fitnessdarck.presentation.screens.authentication.AuthViewModel
import com.ucne.fitnessdarck.presentation.screens.exercises.ExerciseItem
import com.ucne.fitnessdarck.presentation.screens.exercises.ExerciseViewModel

@Composable
fun CreateRoutineScreen(
    routineViewModel: RoutineViewModel = hiltViewModel(),
    exerciseViewModel: ExerciseViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val exercises by exerciseViewModel.exercises.collectAsState(initial = emptyList())
    val currentStep by routineViewModel.currentStep.collectAsState()
    val levels by routineViewModel.levels.collectAsState(initial = emptyList())
    val equipmentList by routineViewModel.equipmentList.collectAsState(initial = emptyList())

    val authState by authViewModel.authState.observeAsState(AuthState.Unauthenticated)
    val email = when (authState) {
        is AuthState.Authenticated -> authViewModel.auth.currentUser?.email ?: ""
        else -> ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (currentStep) {
            0 -> SelectLevelScreen(routineViewModel, levels)
            1 -> SelectEquipmentScreen(routineViewModel, equipmentList)
            2 -> SelectForceOrMuscleScreen(routineViewModel)
            3 -> {
                LaunchedEffect(
                    routineViewModel.selectedLevel.value,
                    routineViewModel.selectedEquipment.value,
                    routineViewModel.selectedForceOrMuscle.value
                ) {
                    val level = routineViewModel.selectedLevel.value
                    val equipment = routineViewModel.selectedEquipment.value
                    val forceOrMuscle = routineViewModel.selectedForceOrMuscle.value

                    if (level.isNotEmpty() && equipment.isNotEmpty() && forceOrMuscle.isNotEmpty()) {
                        exerciseViewModel.getExercisesByCriteria(
                            level = level,
                            equipment = equipment,
                            force = if (forceOrMuscle == "Force") "Push" else "",
                            muscle = if (forceOrMuscle == "Muscles") "Chest" else ""
                        )
                    }
                }

                SelectExercisesScreen(routineViewModel, exercises, email)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (currentStep > 0) {
                Button(onClick = { routineViewModel.previousStep() }) {
                    Text(text = "Previous")
                }
            }
            Button(onClick = {
                if (currentStep < 3) {
                    routineViewModel.nextStep()
                } else {
                    routineViewModel.createRoutine(email)
                }
            }) {
                Text(text = if (currentStep < 3) "Next" else "Create Routine")
            }
        }
    }
}

@Composable
fun SelectLevelScreen(routineViewModel: RoutineViewModel, levels: List<String>) {
    Column {
        Text("Select Your Level")
        Spacer(modifier = Modifier.height(16.dp))
        levels.forEach { level ->
            Button(
                onClick = {
                    routineViewModel.selectedLevel.value = level
                    routineViewModel.nextStep()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = level)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SelectEquipmentScreen(routineViewModel: RoutineViewModel, equipmentList: List<String>) {
    Column {
        Text("Select Available Equipment")
        Spacer(modifier = Modifier.height(16.dp))
        equipmentList.forEach { equipment ->
            Button(
                onClick = {
                    routineViewModel.selectedEquipment.value = equipment
                    routineViewModel.nextStep()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = equipment)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SelectForceOrMuscleScreen(routineViewModel: RoutineViewModel) {
    Column {
        Text("Would you like to categorize exercises by Force or Muscles?")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                routineViewModel.selectedForceOrMuscle.value = "Force"
                routineViewModel.nextStep()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Force")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                routineViewModel.selectedForceOrMuscle.value = "Muscles"
                routineViewModel.nextStep()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Muscles")
        }
    }
}

@Composable
fun SelectExercisesScreen(
    routineViewModel: RoutineViewModel,
    exercises: List<ExerciseEntity>,
    email: String
) {
    Column(modifier = Modifier.padding(16.dp)) {
        var routineName by remember { mutableStateOf("") }

        TextField(
            value = routineName,
            onValueChange = { routineName = it },
            label = { Text("Routine Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Select Exercises")
            Button(onClick = {
                routineViewModel.routineName.value = routineName
                routineViewModel.createRoutine(email)
            }) {
                Text(text = "Create Routine")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(exercises) { exercise ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = routineViewModel.selectedExercises.contains(exercise),
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                routineViewModel.selectedExercises.add(exercise)
                            } else {
                                routineViewModel.selectedExercises.remove(exercise)
                            }
                        }
                    )
                    ExerciseItem(exercise = exercise, onClick = {})
                }
            }
        }
    }
}