package com.example.domain.usecase

import com.example.domain.model.HeartRateData
import com.example.domain.repository.IHeartRateHistoryRepository
import kotlinx.coroutines.flow.Flow

class GetHeartRateDataUseCase(private val repository: IHeartRateHistoryRepository) {

    suspend fun getAllHeartListData(): Flow<List<HeartRateData>> {
        return repository.getAllHeartListData()
    }

    suspend fun getSpecificHeartListData(name:String): Flow<List<HeartRateData>> {
        return repository.getHeartRateMeasureData(name)
    }

    suspend fun deleteHeartRateData() {
        repository.deleteHeartRatedata()
    }

    suspend fun exportHeartRateData(): Flow<List<HeartRateData>> {
        return repository.exportHeartRateData()
    }

    suspend fun insertHeartRateData(heartRateData: HeartRateData){
                repository.insertHeartRateData(heartRateData)
    }


}