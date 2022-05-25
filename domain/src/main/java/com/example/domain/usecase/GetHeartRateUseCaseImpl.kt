package com.example.domain.usecase

import com.example.domain.model.HeartRateInfo
import com.example.domain.model.Result
import com.example.domain.repository.HeartRateMeasureRepository

class GetHeartRateUseCaseImpl(private val heartRateMeasureRepository: HeartRateMeasureRepository):GetHeartRateUseCase {
    override suspend operator fun invoke(param: String) = heartRateMeasureRepository.getHeartRateMeasureData(param)

}