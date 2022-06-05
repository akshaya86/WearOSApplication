package com.example.data.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.database.HR_TABLE_NAME

@Entity(tableName = HR_TABLE_NAME)
data class HeartRateEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    var heartRateBpm: Double? = 0.0,
    val time: Long? = 0,
    val name: String? = ""
)
