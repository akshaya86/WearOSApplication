package com.example.domain.usecase

import com.example.domain.repository.HeartRateMeasureRepository

class GetHeartRateUseCase(private val heartRateMeasureRepository: HeartRateMeasureRepository):IGetHeartRateUseCase {
    override suspend fun invoke(param: String) = heartRateMeasureRepository.getHeartRateMeasureData(param)

}