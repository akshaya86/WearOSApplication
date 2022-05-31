package com.example.data.di

import com.example.data.repository.HeartRateMeasureRepository
import com.example.domain.repository.IHeartRateMeasureRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<IHeartRateMeasureRepository> { HeartRateMeasureRepository(get()) }
}