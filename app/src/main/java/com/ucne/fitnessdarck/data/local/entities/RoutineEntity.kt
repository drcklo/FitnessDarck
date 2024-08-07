package com.ucne.fitnessdarck.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true) val routineId: Int = 0,
    val name: String,
    val userId: String,
    val exercises: List<String>
)