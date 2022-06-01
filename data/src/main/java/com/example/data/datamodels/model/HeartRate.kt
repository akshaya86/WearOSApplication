package com.example.data.datamodels.model

import com.example.data.database.entity.HeartDataEntity
import com.example.data.datamodels.base.RoomMapper

data class HeartRate(
    val id: Int? = 0,
    var ratebpm: Double? = 0.0,
    val time: Long? = 0
)

data class HeartInfoResponse(
    val id: Int? = 0,
    val heartRateList: List<HeartRate>?,
    val info :HeartRateData,
    val name: String? = ""
) : RoomMapper<HeartDataEntity> {

    override fun mapToRoomEntity() = HeartDataEntity(id, heartRateList ?: arrayListOf(), info,name ?: "")
}

data class HeartRateData(val heartRate: Int?=0,val time:Long?=0)


