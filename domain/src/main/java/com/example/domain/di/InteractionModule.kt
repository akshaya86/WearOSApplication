package com.example.domain.di

import com.example.domain.usecase.GetHeartRateUseCase
import com.example.domain.usecase.GetHeartRateUseCaseImpl
import org.koin.dsl.module

val interactionModule = module {
    factory<GetHeartRateUseCase> { GetHeartRateUseCaseImpl(get()) }
}
