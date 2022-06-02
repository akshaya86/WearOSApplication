package com.example.domain.di

import com.example.domain.usecase.GetHeartRateDataUseCase
import org.koin.dsl.module

val interactionModule = module {
    factory { GetHeartRateDataUseCase(get()) }

}
