package com.example.data.repository

import com.example.data.database.dao.HeartDataDao
import com.example.data.database.entity.HeartDataEntity
import com.example.domain.model.HeartRateInfo
import com.example.domain.model.Result
import com.example.domain.repository.IHeartRateMeasureRepository

class HeartRateMeasureRepository(
    private val heartDataDao: HeartDataDao
) : BaseRepository<HeartRateInfo, HeartDataEntity>(), IHeartRateMeasureRepository {
    override suspend fun getHeartRateMeasureData(name: String): Result<HeartRateInfo> {
        return fetchData(
            dbDataProvider = { heartDataDao.retrieveHeartRateData(name) }
        )
    }
}