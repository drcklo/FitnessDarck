package com.ucne.fitnessdarck.presentation.screens.routines

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.fitnessdarck.data.local.entities.ExerciseEntity
import com.ucne.fitnessdarck.data.local.entities.RoutineEntity
import com.ucne.fitnessdarck.data.repository.ExerciseRepository
import com.ucne.fitnessdarck.data.repository.RoutineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoutineViewModel @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {

    private val _allRoutines = MutableStateFlow<List<RoutineEntity>>(emptyList())
    val allRoutines: StateFlow<List<RoutineEntity>> = _allRoutines.asStateFlow()

    private val _currentStep = MutableStateFlow(0)
    val currentStep: StateFlow<Int> get() = _currentStep

    val routineName = mutableStateOf("")
    val selectedLevel = mutableStateOf("")
    val selectedEquipment = mutableStateOf("")
    val selectedForceOrMuscle = mutableStateOf("")
    val selectedExercises = mutableStateListOf<ExerciseEntity>()

    private val _levels = MutableStateFlow<List<String>>(emptyList())
    val levels: StateFlow<List<String>> get() = _levels

    private val _equipmentList = MutableStateFlow<List<String>>(emptyList())
    val equipmentList: StateFlow<List<String>> get() = _equipmentList

    val exercises = mutableStateListOf<ExerciseEntity>()

    init {
        fetchRoutines() // Initialize the routines when ViewModel is created
    }

    fun nextStep() {
        _currentStep.value += 1
    }

    fun previousStep() {
        if (_currentStep.value > 0) {
            _currentStep.value -= 1
        }
    }

    private fun fetchRoutines() {
        viewModelScope.launch {
            routineRepository.getAllRoutines()
            val routines = routineRepository.getAllRoutines()
            _allRoutines.value = routines
        }
    }

    fun fetchExercises() {
        viewModelScope.launch {
            val fetchedExercises = exerciseRepository.getExercisesByCriteria(
                selectedLevel.value,
                selectedEquipment.value,
                if (selectedForceOrMuscle.value == "Force") "Push" else "",
                if (selectedForceOrMuscle.value == "Muscles") "Chest" else ""
            )
            exercises.clear()
            exercises.addAll(fetchedExercises)
        }
    }

    fun createRoutine(email: String) {
        viewModelScope.launch {
            val routine = RoutineEntity(
                name = routineName.value,
                userId = email,
                exercises = selectedExercises.map { it.id }
            )
            routineRepository.insertRoutine(routine)
        }
    }
}
