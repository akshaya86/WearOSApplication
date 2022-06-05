package com.example.data.di

import com.example.data.repository.HeartRateMeasureRepository
import com.example.domain.repository.IHeartRateHistoryRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<IHeartRateHistoryRepository> { HeartRateMeasureRepository(get()) }

}