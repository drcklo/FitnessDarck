package com.ucne.fitnessdarck.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.ucne.fitnessdarck.data.local.dao.ExerciseDao
import com.ucne.fitnessdarck.data.local.dao.RoutineDao
import com.ucne.fitnessdarck.data.local.database.ExerciseDb
import com.ucne.fitnessdarck.data.remote.ExerciseApi
import com.ucne.fitnessdarck.ui.theme.DataStoreManager
import com.ucne.fitnessdarck.util.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun provideExerciseApiService(moshi: Moshi): ExerciseApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ExerciseApi::class.java)
    }

    @Singleton
    @Provides
    fun provideExerciseDatabase(@ApplicationContext appContext: Context): ExerciseDb {
        return Room.databaseBuilder(
            appContext,
            ExerciseDb::class.java,
            "exercise_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideExerciseDao(database: ExerciseDb): ExerciseDao {
        return database.exerciseDao()
    }

    @Provides
    @Singleton
    fun provideRoutineDao(database: ExerciseDb): RoutineDao {
        return database.routineDao()
    }
    @Provides
    fun provideDataStoreManager(
        @ApplicationContext context: Context
    ): DataStoreManager {
        return DataStoreManager(context)
    }
}
