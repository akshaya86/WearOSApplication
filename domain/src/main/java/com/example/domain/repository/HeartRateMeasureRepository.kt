package com.example.domain.repository

import com.example.domain.model.HeartRateInfo
import  com.example.domain.model.Result
interface HeartRateMeasureRepository {
    suspend fun getHeartRateMeasureData(name:String):Result<HeartRateInfo>
}