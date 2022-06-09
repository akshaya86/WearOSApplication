package com.example.domain.usecase

import com.example.domain.model.HeartRateData
import com.example.domain.repository.IHeartRateHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetHeartRateOperationsUseCase(private val repository: IHeartRateHistoryRepository) {

    fun getAllHeartListData(): Flow<List<HeartRateData>> {
        return repository.getAllHeartListData()
    }

    suspend fun deleteHeartRateData() {
        repository.deleteHeartRatedata()
    }

    suspend fun insertHeartRateData(heartRateData: HeartRateData) {
        repository.insertHeartRateData(heartRateData)
    }

    suspend fun insertAllHeartRateData(heartRateData: List<HeartRateData>) {
        repository.insertAllHeartRateData(heartRateData)
    }

    fun createCSVDataFormat(heartRateData: List<HeartRateData>) =
        repository.createCSVDataFormat(heartRateData)


}