package com.ucne.fitnessdarck.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey val id: String,
    val name: String?,
    val force: String?,
    val level: String?,
    val mechanic: String?,
    val equipment: String?,
    val primaryMuscles: List<String>?,
    val secondaryMuscles: List<String>?,
    val instructions: List<String>?,
    val category: String?,
    val images: List<String>?
)