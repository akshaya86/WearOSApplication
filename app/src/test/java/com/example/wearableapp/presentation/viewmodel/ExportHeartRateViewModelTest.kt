package com.example.wearableapp.presentation.viewmodel

import com.example.domain.model.HeartRateData
import com.example.domain.usecase.GetHeartRateOperationsUseCase
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestExtension::class)
class ExportHeartRateViewModelTest{

    private val dispatcher = TestCoroutineDispatcher()

    @InjectMockKs
    lateinit var viewModel: ExportHeartRateViewModel

    @InjectMockKs
    private lateinit var useCase: GetHeartRateOperationsUseCase

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        useCase = mockk(relaxed = true)
        viewModel = ExportHeartRateViewModel(useCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `check heart data is retrieved`(){
        val heartList = flowOf(listOf(HeartRateData(20.0,123,"")))
        coEvery { useCase.getAllHeartListData()}.returns(heartList)
        val viewModel = ExportHeartRateViewModel(useCase)
        val result = viewModel.getAllHearRateData()
        assertNotNull(result)
    }

    @Test
    fun `check export heart rate data`():Unit = runBlocking {
        val heartList = listOf(HeartRateData(20.0,123,""))
        val list: MutableList<HeartRateData> = mutableListOf()
        var result = false

        coEvery { useCase.createCSVDataFormat(heartList) }.answers {
            list.addAll(heartList)
        }.andThen(!result)
        viewModel.startDataExporting(heartList)

        Truth.assertThat(list).isNotEqualTo(false)
        Truth.assertThat(!result).isTrue()
    }

    @Test
    fun `check deleted data`(){
        val result = viewModel.deleteHeartRateData()
        coEvery { useCase.deleteHeartRateData() }

        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `check exported status value added`(): Unit = runBlocking {
        val heartList = listOf(HeartRateData(20.0,123,""))
        val list: MutableList<HeartRateData> = mutableListOf()
        coEvery { useCase.createCSVDataFormat(heartList) }.answers {
            list.addAll(heartList)
        }
        viewModel.startDataExporting(heartList)
        viewModel.exportStatus.postValue( "Started")

        Truth.assertThat(viewModel.exportStatus.value?.isNotEmpty())
        Truth.assertThat(viewModel.exportStatus.value)
    }

    @Test
    fun `check heart rate live model`() = runBlocking {
        val list = listOf(HeartRateData(10.0, 123, ""))
        viewModel.heartRateData.postValue(list)
        val nList = mutableListOf<HeartRateData>()
         viewModel.heartRateData.observeForever {
            nList.addAll(it)
        }

        Truth.assertThat(viewModel.heartRateData.value).isNotEmpty()
    }

    @Test
    fun `ViewModel posts on heartLiveData`() {
        val list = listOf(HeartRateData(10.0, 123, ""))
        val viewModel = ExportHeartRateViewModel(useCase)
        viewModel.heartRateData.postValue(list)
        dispatcher.advanceUntilIdle()
        assertEquals(list, viewModel.heartRateData.value)
    }
}