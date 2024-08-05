package com.ucne.fitnessdarck.data.repository

import com.ucne.fitnessdarck.data.local.dao.ExerciseDao
import com.ucne.fitnessdarck.data.local.entities.ExerciseEntity
import com.ucne.fitnessdarck.data.remote.ExerciseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import java.io.IOException

@Singleton
class ExerciseRepository @Inject constructor(
    private val api: ExerciseApi,
    private val exerciseDao: ExerciseDao
) {
    suspend fun fetchAndSaveExercises(page: Int, size: Int) {
        withContext(Dispatchers.IO) {
            try {
                val exercises = api.getExercises(page, size)
                exerciseDao.insertAll(exercises)
            } catch (e: IOException) {
                // Handle network error here
                e.printStackTrace()
            } catch (e: Exception) {
                // Handle other types of errors
                e.printStackTrace()
            }
        }
    }

    suspend fun getAllExercises(limit: Int, offset: Int): List<ExerciseEntity> {
        return withContext(Dispatchers.IO) {
            exerciseDao.getExercises(limit, offset)
        }
    }
}
