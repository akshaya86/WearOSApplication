package com.example.wearableapp.di

import com.example.wearableapp.presentation.viewmodel.MainViewModel
import com.example.wearableapp.presentation.viewmodel.MeasureHeartRateViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


    val viewModelModule = module {
        viewModel { MainViewModel() }
        viewModel { MeasureHeartRateViewModel(get()) }
    }