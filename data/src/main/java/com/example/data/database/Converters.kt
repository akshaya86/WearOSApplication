package com.example.data.database

import androidx.room.TypeConverter
import com.example.data.database.entity.HeartRateEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
  private val gson = Gson()
  
  // Heart Rate Data list converters
  
  @TypeConverter
  fun fromHeartListToJson(list: List<HeartRateEntity>?): String {
    return list?.let { gson.toJson(it) } ?: ""
  }
  
  @TypeConverter
  fun fromJsonToHeartList(jsonList: String): List<HeartRateEntity> {
    val listType = object : TypeToken<List<HeartRateEntity>>() {}.type
    return gson.fromJson(jsonList, listType)
  }
  
  // MainInfo converters
  
  @TypeConverter
  fun fromMainInfoToJson(mainInfo: HeartRateEntity?): String {
    return mainInfo?.let { gson.toJson(it) } ?: ""
  }
  
  @TypeConverter
  fun fromJsonToMainInfo(json: String): HeartRateEntity {
    val type = object : TypeToken<HeartRateEntity>() {}.type
    return gson.fromJson(json, type)
  }
}