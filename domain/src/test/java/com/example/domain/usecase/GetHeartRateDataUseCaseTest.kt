package com.example.domain.usecase

import com.example.domain.model.HeartRateData
import com.example.domain.repository.IHeartRateHistoryRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.jupiter.api.AfterEach

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetHeartRateDataUseCaseTest{

    private val dispatcher = TestCoroutineDispatcher()

    lateinit var repository: IHeartRateHistoryRepository
    lateinit var useCase: GetHeartRateDataUseCase
    private val heartData = HeartRateData(10.0, 123,"")

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mockk(relaxed = true)
        useCase = GetHeartRateDataUseCase(repository)
    }

    @Test
    fun `check data inserted into repository`()= runBlocking {

        val expectedList = listOf(HeartRateData(heartRateBpm = 10.0, time = 123, name = ""))
        var actualList = mutableListOf<HeartRateData>()

        coEvery { repository.insertAllHeartRateData(expectedList) } answers {
            actualList = expectedList.toMutableList()
        }

        useCase.insertAllHeartRateData(listOf(heartData))
        assertThat(actualList.containsAll(expectedList)).isTrue()
        assertThat(actualList).isEqualTo(expectedList)
    }

    @Test
    fun `check inserting single data into repository`() = runBlocking {
        var actualList = mutableListOf<HeartRateData>()
        coEvery { repository.insertHeartRateData(heartData) } answers {
            actualList.add(heartData)
        }

        useCase.insertHeartRateData(heartData)
        assertThat(actualList.contains(heartData)).isTrue()
        assertThat(actualList).isNotEmpty()
    }

    @Test
    fun `check heart data retrieve from repository`() = runBlocking {
        val expectedList = listOf(heartData)

        every { repository.getAllHeartListData() } returns flowOf(expectedList)

        assertThat(useCase.getAllHeartListData().take(1).toList()).containsExactly(listOf(heartData)).inOrder()
        assertThat(expectedList).isNotEmpty()
        assertThat(useCase.getAllHeartListData().count()).isNotNull()
    }

    @Test
    fun `check creating csv file from retrieved data`(){

        val expectedList = listOf(heartData)
        val list: MutableList<HeartRateData> = mutableListOf()

        coEvery { repository.createCSVDataFormat(expectedList) } answers {
            list.addAll(expectedList)
        }

        useCase.createCSVDataFormat(expectedList)
        Truth.assertThat(list.containsAll(expectedList)).isTrue()
        Truth.assertThat(list.contains(heartData)).isTrue()
    }

    @Test
    fun `check delete heart data from repository`() = runBlocking {

        useCase.deleteHeartRateData()
        Assert.assertEquals(repository.deleteHeartRatedata(),Unit)
}


}