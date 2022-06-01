package com.example.domain.repository

import com.example.domain.model.HeartRateInfo
import  com.example.domain.model.Result
interface IHeartRateMeasureRepository {
    suspend fun getHeartRateMeasureData(name:String):Result<HeartRateInfo>
}