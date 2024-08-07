package com.ucne.fitnessdarck.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ucne.fitnessdarck.data.local.dao.ExerciseDao
import com.ucne.fitnessdarck.data.local.dao.RoutineDao
import com.ucne.fitnessdarck.data.local.entities.ExerciseEntity
import com.ucne.fitnessdarck.data.local.entities.RoutineEntity
import com.ucne.fitnessdarck.util.Converters

@Database(
    entities = [
        ExerciseEntity::class,
        RoutineEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExerciseDb : RoomDatabase() {
    abstract fun exerciseDao(): ExerciseDao
    abstract fun routineDao(): RoutineDao
}