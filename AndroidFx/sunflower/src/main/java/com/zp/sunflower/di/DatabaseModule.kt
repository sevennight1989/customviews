package com.zp.sunflower.di

import android.content.Context
import com.zp.sunflower.data.AppDatabase
import com.zp.sunflower.data.GardenPlantingDao
import com.zp.sunflower.data.PlantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun providePlantDao(appDatabase: AppDatabase): PlantDao = appDatabase.plantDao()

    @Provides
    fun provideGardenPlantingDao(appDatabase: AppDatabase): GardenPlantingDao =
        appDatabase.gardenPlantingDao()

}