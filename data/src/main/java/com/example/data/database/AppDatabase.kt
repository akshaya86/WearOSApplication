package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.dao.HeartDataDao
import com.example.data.database.entity.HeartDataEntity

@Database(entities = [HeartDataEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase:RoomDatabase() {
    abstract fun heartDataDao():HeartDataDao
}