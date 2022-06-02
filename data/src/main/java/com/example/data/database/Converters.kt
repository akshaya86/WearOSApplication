package com.example.data.database

import androidx.room.TypeConverter
import com.example.data.datamodels.model.HeartRate
import com.example.data.datamodels.model.HeartRateData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
  private val gson = Gson()
  
  // Heart Rate Data list converters
  
  @TypeConverter
  fun fromHeartListToJson(list: List<HeartRate>?): String {
    return list?.let { gson.toJson(it) } ?: ""
  }
  
  @TypeConverter
  fun fromJsonToHeartList(jsonList: String): List<HeartRate> {
    val listType = object : TypeToken<List<HeartRate>>() {}.type
    return gson.fromJson(jsonList, listType)
  }
  
  // MainInfo converters
  
  @TypeConverter
  fun fromMainInfoToJson(mainInfo: HeartRateData?): String {
    return mainInfo?.let { gson.toJson(it) } ?: ""
  }
  
  @TypeConverter
  fun fromJsonToMainInfo(json: String): HeartRateData {
    val type = object : TypeToken<HeartRateData>() {}.type
    return gson.fromJson(json, type)
  }
}