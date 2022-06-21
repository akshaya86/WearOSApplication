package com.example.wearableapp.presentation.viewmodel

import com.example.wearableapp.presentation.model.MenuItem
import com.google.common.truth.Truth
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()

    @InjectMockKs
    lateinit var viewModel : MainViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = MainViewModel()
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `check menu item list contain any element from dummy list`() {

        val list = listOf(MenuItem("Measure HR"),MenuItem("Export HR"),
            MenuItem("Item 3"),MenuItem("Item 4"))
        val orgList = viewModel.getMenuList()
        Truth.assertThat(orgList).containsAnyIn(list)
    }

    @Test
    fun `check list is not empty`(){
        val orgList = viewModel.getMenuList()
        Truth.assertThat(orgList).isNotEmpty()
    }

    @Test
    fun `check list contain required Measure HR data`(){
        val orgList = viewModel.getMenuList().toString()
        Truth.assertThat(orgList).contains("Measure HR")
    }

    @Test
    fun `check list contain required Export HR data`(){
        val orgList = viewModel.getMenuList().toString()
        Truth.assertThat(orgList).contains("Export HR Data")
    }

}