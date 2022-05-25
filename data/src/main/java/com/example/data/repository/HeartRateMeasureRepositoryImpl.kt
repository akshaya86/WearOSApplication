package com.example.data.repository

import com.example.data.database.dao.HeartDataDao
import com.example.data.database.entity.HeartDataEntity
import com.example.data.network.HeartRateApi
import com.example.data.network.base.getData
import com.example.domain.model.HeartRateInfo
import com.example.domain.model.Result
import com.example.domain.repository.HeartRateMeasureRepository

class HeartRateMeasureRepositoryImpl(
    private val heartRateApi: HeartRateApi,
    private val heartDataDao: HeartDataDao
) : BaseRepository<HeartRateInfo, HeartDataEntity>(), HeartRateMeasureRepository {
    override suspend fun getHeartRateMeasureData(name: String): Result<HeartRateInfo> {
        return fetchData(
            apiDataProvider = {
                heartRateApi.uploadHeartRate().getData(
                    fetchFromCacheAction = { heartDataDao.getSaveHeartRateData(name) },
                    cacheAction = { heartDataDao.saveHeartRateData(it) }
                )
            },
            dbDataProvider = { heartDataDao.getSaveHeartRateData(name) }
        )
    }
}