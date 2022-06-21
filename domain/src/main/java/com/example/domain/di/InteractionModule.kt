package com.example.domain.di

import com.example.domain.usecase.GetHeartRateOperationsUseCase
import org.koin.dsl.module

val interactionModule = module {
    factory { GetHeartRateOperationsUseCase(get()) }

}
