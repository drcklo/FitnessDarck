package com.ucne.fitnessdarck.data.repository

import com.ucne.fitnessdarck.data.local.dao.RoutineDao
import com.ucne.fitnessdarck.data.local.entities.RoutineEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoutineRepository @Inject constructor(
    private val routineDao: RoutineDao
) {
    suspend fun insertRoutine(routine: RoutineEntity) {
        routineDao.insertRoutine(routine)
    }

    suspend fun getAllRoutines(): List<RoutineEntity> {
        return routineDao.getAllRoutines()
    }

    suspend fun getRoutinesByUser(userId: String): List<RoutineEntity> {
        return routineDao.getRoutinesByUser(userId)
    }
}