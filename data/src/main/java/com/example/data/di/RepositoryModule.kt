package com.example.data.di

import com.example.data.common.Connectivity
import com.example.data.common.ConnectivityImpl
import com.example.data.repository.HeartRateMeasureRepositoryImpl
import com.example.domain.repository.HeartRateMeasureRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<HeartRateMeasureRepository> { HeartRateMeasureRepositoryImpl(get(), get()) }
    factory<Connectivity> { ConnectivityImpl(androidContext()) }
}