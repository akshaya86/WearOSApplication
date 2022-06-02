package com.example.domain.usecase

import com.example.domain.base.IBaseUseCase
import com.example.domain.model.HeartRateInfo
import com.example.domain.model.Result

interface IGetHeartRateUseCase : IBaseUseCase<String, HeartRateInfo> {
    override suspend fun invoke(param: String): Result<HeartRateInfo>
}