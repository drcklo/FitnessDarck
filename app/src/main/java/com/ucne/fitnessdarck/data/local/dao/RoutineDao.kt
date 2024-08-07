package com.ucne.fitnessdarck.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucne.fitnessdarck.data.local.entities.RoutineEntity

@Dao
interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoutine(routine: RoutineEntity)

    @Query("SELECT * FROM routines")
    suspend fun getAllRoutines(): List<RoutineEntity>

    @Query("SELECT * FROM routines WHERE userId = :userId")
    suspend fun getRoutinesByUser(userId: String): List<RoutineEntity>
}