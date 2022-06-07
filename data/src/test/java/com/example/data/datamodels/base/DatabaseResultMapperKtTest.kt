package com.example.data.datamodels.base

import com.example.data.database.entity.HeartRateEntity
import com.example.domain.model.HeartRateData
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test


class DatabaseResultMapperKtTest{

    @Test
    fun `check model to entity mapping`(){
        val heartList = listOf(HeartRateData(20.0,123,""))
        val entityData = heartList.map {
            it.toHeartRateEntity()
        }
        assertThat(entityData).isNotEmpty()
        assertThat(heartList[0].heartRateBpm).isEqualTo(entityData[0].heartRateBpm)
        assertThat(heartList.size).isEqualTo(entityData.size)
    }

    @Test
    fun `check entity to model mapping`(){
        val entityData = HeartRateEntity(0,20.0,123,"")
        val heartListItem = HeartRateData(20.0,123,"")
        val heartData = entityData.toHeartRateData()

        assertThat(heartData).isNotNull()
        assertThat(heartData.heartRateBpm).isEqualTo(heartListItem.heartRateBpm)
        assertThat(heartData).isEqualTo(heartListItem)
    }

}