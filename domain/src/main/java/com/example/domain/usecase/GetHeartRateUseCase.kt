package com.example.domain.usecase

import com.example.domain.repository.IHeartRateMeasureRepository

class GetHeartRateUseCase(private val heartRateMeasureRepository: IHeartRateMeasureRepository):IGetHeartRateUseCase {
    override suspend fun invoke(param: String) = heartRateMeasureRepository.getHeartRateMeasureData(param)

}