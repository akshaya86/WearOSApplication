package com.example.wearableapp

import android.app.Application
import android.content.Context
import com.example.data.appModule
import com.example.data.di.databaseModule
import com.example.data.di.networkingModule
import com.example.data.di.repositoryModule
import com.example.domain.di.interactionModule
import com.example.wearableapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WearOsApp:Application() {
    companion object {
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@WearOsApp)
            modules(appModules+dataModules+domainModules)
        }
    }

    val appModules = listOf(appModule, viewModelModule )
    val dataModules = listOf(networkingModule, databaseModule, repositoryModule)
    val domainModules = listOf(interactionModule)

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}