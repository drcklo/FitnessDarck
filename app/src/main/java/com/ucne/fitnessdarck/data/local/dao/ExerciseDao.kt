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

    @Query("SELECT * FROM exercises WHERE level = :level")
    suspend fun getExercisesByLevel(level: String): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE equipment = :equipment")
    suspend fun getExercisesByEquipment(equipment: String): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE force = :force")
    suspend fun getExercisesByForce(force: String): List<ExerciseEntity>

    @Query(
        """
        SELECT * 
        FROM exercises 
        WHERE primaryMuscles 
        LIKE :muscles"""
    )
    suspend fun getExercisesByMuscles(muscles: String): List<ExerciseEntity>

    @Query(
        """
        SELECT * FROM exercises 
        WHERE (:level IS NULL OR level = :level)
        AND (:equipment IS NULL OR equipment = :equipment)
        AND (:force IS NULL OR force = :force)
        AND (:muscle IS NULL OR primaryMuscles LIKE '%' || :muscle || '%')
    """
    )
    suspend fun getExercisesByCriteria(
        level: String,
        equipment: String,
        force: String,
        muscle: String
    ): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE id IN (:ids)")
    suspend fun getExercisesByIds(ids: List<String>): List<ExerciseEntity>
}