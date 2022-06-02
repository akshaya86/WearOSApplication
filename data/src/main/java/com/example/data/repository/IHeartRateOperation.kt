package com.example.data.repository

import com.example.data.database.entity.HeartRateEntity
import com.example.domain.model.HeartRateData

interface IHeartRateOperation {

    suspend fun insertHeartRateData(heartRateInfo: HeartRateData)

    suspend fun retrieveHeartRateData(name:String):List<HeartRateData>

}