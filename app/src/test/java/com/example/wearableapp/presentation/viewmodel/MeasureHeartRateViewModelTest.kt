package com.example.wearableapp.presentation.viewmodel

import com.example.domain.model.HeartRateData
import com.example.domain.usecase.GetHeartRateDataUseCase
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.collections.ArrayList

class MeasureHeartRateViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    @InjectMockKs
    lateinit var viewModel: MeasureHeartRateViewModel

    @InjectMockKs
    private lateinit var useCase: GetHeartRateDataUseCase

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        useCase = mockk(relaxed = true)
        viewModel = MeasureHeartRateViewModel(useCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `check heart data list is matching with content`() {

        val heartRateData = HeartRateData(0.0, 0)
        val mockHeartList = mutableListOf<HeartRateData>()
        mockHeartList.add(heartRateData)
        viewModel.setDummyHeartData(mockHeartList as ArrayList<HeartRateData>)

        Truth.assertThat(mockHeartList).containsExactlyElementsIn(listOf(heartRateData))
    }

    @Test
    fun `check heart data list is not empty`() {

        val heartRateData = HeartRateData(0.0, 0)
        val mockHeartList = mutableListOf<HeartRateData>()
        mockHeartList.add(heartRateData)
        viewModel.setDummyHeartData(mockHeartList as ArrayList<HeartRateData>)

        Truth.assertThat(mockHeartList).doesNotContain(listOf(heartRateData))
        Truth.assertThat(mockHeartList).isNotEmpty()
        Truth.assertThat(mockHeartList).containsAtLeastElementsIn(listOf(heartRateData))
    }

    @Test
    fun `check heart data insertion viewmodel`(): Unit = runBlocking {
        val heartList = HeartRateData(20.0, 1000, "")

        var mockList = mutableListOf<HeartRateData>()
        mockList.add(heartList)
        viewModel.insertData(mockList)
        Truth.assertThat(mockList).isNotEmpty()
    }

    @Test
    fun `check heart data insertion usecase`(): Unit = runBlocking {
        val hr_bpm = 20.0
        val heartData = HeartRateData(hr_bpm, 123,"")

        val expectedList = listOf(HeartRateData(heartRateBpm = hr_bpm, time = 123, name = ""))
        var actualList = mutableListOf<HeartRateData>()

        coEvery { useCase.insertAllHeartRateData(expectedList) } answers {
            actualList = expectedList.toMutableList()
        }

        viewModel.insertData(listOf(heartData))
        Truth.assertThat(actualList).isNotEqualTo(expectedList)

    }




}