package com.example.data

import androidx.appcompat.app.AppCompatActivity
import com.example.data.coroutine.CoroutineContextProvider
import org.koin.dsl.module

val appModule = module {
    single { CoroutineContextProvider() }
}