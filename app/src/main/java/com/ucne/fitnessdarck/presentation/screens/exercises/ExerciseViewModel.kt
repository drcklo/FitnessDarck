package com.ucne.fitnessdarck.presentation.screens.exercises

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.fitnessdarck.data.local.entities.ExerciseEntity
import com.ucne.fitnessdarck.data.repository.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _exercises = MutableStateFlow<List<ExerciseEntity>>(emptyList())
    val exercises: StateFlow<List<ExerciseEntity>> = _exercises.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    var currentPage = 1
        private set
    private val pageSize = 10
    private var endReached = false

    init {
        fetchAndSaveExercises()
    }

    private fun fetchAndSaveExercises() {
        if (_isLoading.value || endReached) return

        viewModelScope.launch {
            _isLoading.value = true
            repository.fetchAndSaveExercises(currentPage, pageSize)
            val newExercises = repository.getAllExercises(pageSize, (currentPage - 1) * pageSize)
            if (newExercises.isNotEmpty()) {
                _exercises.value += newExercises
                currentPage++
            } else {
                endReached = true
            }
            _isLoading.value = false
        }
    }

    fun loadMoreExercises() {
        fetchAndSaveExercises()
    }

    fun getExercisesByCriteria(level: String, equipment: String, force: String, muscle: String) {
        viewModelScope.launch {
            val fetchedExercises =
                repository.getExercisesByCriteria(level, equipment, force, muscle)
            _exercises.value = fetchedExercises
        }
    }

    fun getPageSize() = pageSize

    fun getExercisesByIds(ids: List<String>): StateFlow<List<ExerciseEntity>> {
        val resultFlow = MutableStateFlow<List<ExerciseEntity>>(emptyList())
        viewModelScope.launch {
            resultFlow.value = repository.getExercisesByIds(ids)
        }
        return resultFlow.asStateFlow()
    }
}
