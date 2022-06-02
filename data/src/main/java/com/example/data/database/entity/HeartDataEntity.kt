package com.example.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.HR_TABLE_NAME
import com.example.data.datamodels.base.DomainMapper
import com.example.data.datamodels.model.HeartRate
import com.example.data.datamodels.model.HeartRateData
import com.example.domain.model.HeartRateInfo

@Entity(tableName = HR_TABLE_NAME)
data class HeartDataEntity(@PrimaryKey val id: Int? = 0,
                           val heartList: List<HeartRate>?,
                           @Embedded
                           val main: HeartRateData?,
                           val name: String? = "") : DomainMapper<HeartRateInfo> {
  
  override fun mapToDomainModel() = HeartRateInfo(main?.heartRate?:0,main?.time?:0)
}
