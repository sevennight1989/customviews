package com.zp.sunflower.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GardenPlantingDao {
    @Query("SELECT * FROM garden_plantings")
    fun getGardenPlantings(): Flow<List<GardenPlanting>>

    @Query("SELECT EXISTS (SELECT 1 FROM garden_plantings WHERE plant_id = :plantId LIMIT 1) ")
    fun isPlanted(plantId: String): Flow<Boolean>

    @Transaction
    @Query("SELECT * FROM plants WHERE id IN (SELECT DISTINCT(plant_id) FROM garden_plantings)")
    fun getPantedGardens(): Flow<List<PlantAndGardenPlantings>>

    @Insert
    suspend fun insertGardenPlanting(gardenPlanting: GardenPlanting): Long

    @Delete
    suspend fun deleteGardenPlanting(gardenPlanting: GardenPlanting)
}