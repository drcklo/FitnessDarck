package com.ucne.fitnessdarck.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ucne.fitnessdarck.data.local.entities.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(exercises: List<ExerciseEntity>)

    @Query("SELECT * FROM exercises LIMIT :limit OFFSET :offset")
    suspend fun getExercises(limit: Int, offset: Int): List<ExerciseEntity>
}