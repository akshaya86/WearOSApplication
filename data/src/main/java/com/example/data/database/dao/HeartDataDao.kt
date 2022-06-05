package com.example.data.database.dao

import androidx.room.*
import com.example.data.database.HR_TABLE_NAME
import com.example.data.database.entity.HeartRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeartDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeartRateData(heartRate: HeartRateEntity)

    @Query("SELECT * FROM HEARTRATE")
    fun retrieveAllHeartRateData(): Flow<List<HeartRateEntity>>

    @Query("DELETE FROM HEARTRATE")
    suspend fun deleteHeartRateData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllHeartRateData(list: List<HeartRateEntity>)
}