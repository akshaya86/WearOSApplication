package com.example.data

import androidx.appcompat.app.AppCompatActivity
import com.example.data.coroutine.CoroutineContextProvider
import com.example.wearableapp.presentation.navigator.AppNavigator
import org.koin.dsl.module

val appModule = module {
    single { CoroutineContextProvider() }
    single { (activity: AppCompatActivity) -> AppNavigator(activity) }
}