package com.example.domain.repository

import com.example.domain.model.HeartRateData
import kotlinx.coroutines.flow.Flow

interface IHeartRateHistoryRepository {

    suspend fun getHeartRateMeasureData(name:String):Flow<List<HeartRateData>>

    suspend fun deleteHeartRatedata()

    suspend fun exportHeartRateData():Flow<List<HeartRateData>>

    suspend fun getAllHeartListData():Flow<List<HeartRateData>>

    fun insertHeartRateData(heartRateData: HeartRateData)

}