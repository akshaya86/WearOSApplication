package com.example.domain.di

import com.example.domain.usecase.IGetHeartRateUseCase
import com.example.domain.usecase.GetHeartRateUseCase
import org.koin.dsl.module

val interactionModule = module {
    factory<IGetHeartRateUseCase> { GetHeartRateUseCase(get()) }
}
