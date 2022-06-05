package com.example.domain.repository

import com.example.domain.model.HeartRateData
import kotlinx.coroutines.flow.Flow

interface IHeartRateHistoryRepository {

    suspend fun deleteHeartRatedata()

    fun getAllHeartListData():Flow<List<HeartRateData>>

    fun insertHeartRateData(heartRateData: HeartRateData)

    fun insertAllHeartRateData(heartRateData: List<HeartRateData>)

}