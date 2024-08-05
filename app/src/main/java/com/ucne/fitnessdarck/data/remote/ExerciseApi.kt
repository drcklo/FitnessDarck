package com.ucne.fitnessdarck.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.ucne.fitnessdarck.data.local.entities.ExerciseEntity
import com.ucne.fitnessdarck.util.Constants.Companion.GET_EXERCISES

interface ExerciseApi {
    @GET(GET_EXERCISES)
    suspend fun getExercises(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ExerciseEntity>
}