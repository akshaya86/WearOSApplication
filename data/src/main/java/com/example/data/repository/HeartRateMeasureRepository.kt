package com.example.data.repository

import com.example.data.database.dao.HeartDataDao
import com.example.data.datamodels.base.toHeartRateData
import com.example.data.datamodels.base.toHeartRateEntity
import com.example.domain.model.HeartRateData
import com.example.domain.repository.IHeartRateHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.KoinComponent

class HeartRateMeasureRepository(private val heartDataDao: HeartDataDao) :
    IHeartRateHistoryRepository, KoinComponent {


    override suspend fun deleteHeartRatedata() {
        heartDataDao.deleteHeartRateData()
    }

    override fun getAllHeartListData(): Flow<List<HeartRateData>> {
        return heartDataDao.retrieveAllHeartRateData().map {
            it.map { data -> data.toHeartRateData() }
        }
    }

    override fun insertHeartRateData(heartRateInfo: HeartRateData) {
        heartDataDao.insertHeartRateData(heartRateInfo.toHeartRateEntity())
    }

    override fun insertAllHeartRateData(heartRateData: List<HeartRateData>) {
        heartDataDao.insertAllHeartRateData(heartRateData.map { it.toHeartRateEntity() }) }




}