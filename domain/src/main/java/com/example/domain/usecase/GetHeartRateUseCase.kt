package com.example.domain.usecase

import com.example.domain.base.BaseUseCase
import com.example.domain.model.HeartRateInfo
import com.example.domain.model.Result

interface GetHeartRateUseCase : BaseUseCase<String, HeartRateInfo> {
    override suspend operator fun invoke(param: String): Result<HeartRateInfo>
}