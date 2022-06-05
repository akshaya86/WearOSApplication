package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.HeartDataDao
import com.example.data.database.entity.HeartRateEntity

@Database(entities = [HeartRateEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun heartDataDao():HeartDataDao
}