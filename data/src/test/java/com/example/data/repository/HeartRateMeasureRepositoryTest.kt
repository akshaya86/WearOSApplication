package com.example.data.repository

import com.example.data.database.dao.HeartDataDao
import com.example.data.database.entity.HeartRateEntity
import com.example.data.datamodels.base.toHeartRateEntity
import com.example.data.repository.HeartRateMeasureRepository.Companion.convertToCSV
import com.example.domain.model.HeartRateData
import com.example.domain.repository.IHeartRateHistoryRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class HeartRateMeasureRepositoryTest {

    lateinit var iHistoryRepository: IHeartRateHistoryRepository
    lateinit var heartDataDao: HeartDataDao
    lateinit var repository: HeartRateMeasureRepository
    val actualData = HeartRateData(20.0, 123, "")

    @BeforeEach
    fun setUp() {
        iHistoryRepository = mockk(relaxed = true)
        heartDataDao = mockk(relaxed = true)
        repository = HeartRateMeasureRepository(heartDataDao)
    }

    @Test
    fun `check data is inserted `() {
        val expectedEntity = HeartRateEntity(0, 20.0, 123, "")
        val heartDataList = mutableListOf<HeartRateEntity>()

        coEvery { heartDataDao.insertHeartRateData(expectedEntity) } answers {
            heartDataList.add(expectedEntity)
        }

        repository.insertHeartRateData(actualData)
        assertThat(heartDataList.contains(expectedEntity)).isTrue()
        assertThat(heartDataList).isNotEmpty()
        assertThat(heartDataList.first().id).isEqualTo(expectedEntity.id)
        assertThat(heartDataList).containsAtLeastElementsIn(listOf(actualData.toHeartRateEntity()))
            .inOrder()
    }

    @Test
    fun `check bulk data is inserted `() {
        val expectedEntity = listOf(HeartRateEntity(0, 20.0, 123, ""))
        val heartDataList = mutableListOf<HeartRateEntity>()

        coEvery { heartDataDao.insertAllHeartRateData(expectedEntity) } answers {
            heartDataList.addAll(expectedEntity)
        }


        repository.insertAllHeartRateData(listOf(actualData))
        assertThat(heartDataList.containsAll(expectedEntity)).isTrue()
        assertThat(heartDataList).isNotEmpty()
        assertThat(heartDataList).containsAtLeastElementsIn(listOf(actualData.toHeartRateEntity()))
            .inOrder()
    }

    @Test
    fun `check file is converted into csv`() {
        val convertedData = "20.0,123,"
        Truth.assertThat(actualData.convertToCSV()).isEqualTo(convertedData)
    }

    @Test
    fun `check for saved data into database`() = runBlocking {
        val expectedEntity = listOf(HeartRateEntity(0, 20.0, 123, ""))

        coEvery { heartDataDao.retrieveAllHeartRateData() } returns flowOf(expectedEntity)


        assertThat(repository.getAllHeartListData().toList().contains(listOf(actualData))).isTrue()
        assertThat(expectedEntity).isNotEmpty()
        assertThat(repository.getAllHeartListData().count()).isNotNull()
    }

    @Test
    fun `check data is deleted from dataabase`() = runBlocking {

        val result = repository.deleteHeartRatedata()
        assertThat(result).isNotNull()
    }

    @Test
    fun `check csv file is created or not`(){

        assertThat(repository.createCSVDataFormat(listOf(actualData))).isNotNull()
    }


}