package com.ucne.fitnessdarck.data.repository

import com.ucne.fitnessdarck.data.local.dao.ExerciseDao
import com.ucne.fitnessdarck.data.local.entities.ExerciseEntity
import com.ucne.fitnessdarck.data.remote.ExerciseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepository @Inject constructor(
    private val api: ExerciseApi, private val exerciseDao: ExerciseDao
) {
    suspend fun fetchAndSaveExercises(page: Int, size: Int) {
        withContext(Dispatchers.IO) {
            try {
                val exercises = api.getExercises(page, size)
                exerciseDao.insertAll(exercises)
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun getAllExercises(limit: Int, offset: Int): List<ExerciseEntity> {
        return withContext(Dispatchers.IO) {
            exerciseDao.getExercises(limit, offset)
        }
    }

    suspend fun getExercisesByLevel(level: String): List<ExerciseEntity> {
        return exerciseDao.getExercisesByLevel(level)
    }

    suspend fun getExercisesByEquipment(equipment: String): List<ExerciseEntity> {
        return exerciseDao.getExercisesByEquipment(equipment)
    }

    suspend fun getExercisesByForce(force: String): List<ExerciseEntity> {
        return exerciseDao.getExercisesByForce(force)
    }

    suspend fun getExercisesByMuscles(muscles: String): List<ExerciseEntity> {
        return exerciseDao.getExercisesByMuscles(muscles)
    }

    suspend fun getExercisesByCriteria(
        level: String, equipment: String, force: String, muscle: String
    ): List<ExerciseEntity> {
        return exerciseDao.getExercisesByCriteria(level, equipment, force, muscle)
    }

    suspend fun getExercisesByIds(ids: List<String>): List<ExerciseEntity> {
        return exerciseDao.getExercisesByIds(ids)
    }
}
