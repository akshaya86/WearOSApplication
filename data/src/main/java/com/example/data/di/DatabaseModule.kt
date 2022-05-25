package com.example.data.di

import androidx.room.Room
import com.example.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val WEATHER_DB = "app-database"

val databaseModule = module {
  single {
    Room.databaseBuilder(androidContext(), AppDatabase::class.java, WEATHER_DB).fallbackToDestructiveMigration().build()
  }
  factory { get<AppDatabase>().heartDataDao() }
}