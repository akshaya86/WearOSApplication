package com.example.data.datamodels.base

import com.example.data.database.entity.HeartRateEntity
import com.example.domain.model.HeartRateData

fun HeartRateEntity.toHeartRateData(): HeartRateData {
    return HeartRateData(
        this.heartRateBpm,
        this.time,
        this.name
    )
}

fun HeartRateData.toHeartRateEntity(): HeartRateEntity {
    return HeartRateEntity(
            heartRateBpm = heartRateBpm,
            time = time,
            name = name
    )
}