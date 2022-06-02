package com.example.data.database.dao

import androidx.room.*
import com.example.data.database.HR_TABLE_NAME
import com.example.data.database.entity.HeartDataEntity

@Dao
interface HeartDataDao {

    @Transaction
    suspend fun updateHeartDetails(heartData: HeartDataEntity): HeartDataEntity {
        insertHeartRateData(heartData)
        return retrieveHeartRateData(heartData.name ?: "")
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeartRateData(heartRate: HeartDataEntity)

    @Query("SELECT * FROM $HR_TABLE_NAME WHERE name = :name LIMIT 1")
    suspend fun retrieveHeartRateData(name: String): HeartDataEntity
}