package com.example.wearableapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.domain.model.HeartRateData
import com.example.domain.usecase.GetHeartRateDataUseCase
import kotlinx.coroutines.*

class MeasureHeartRateViewModel(private val getHeartRateUseCase :GetHeartRateDataUseCase): ViewModel() {

    var heartLiveData = MutableLiveData<List<HeartRateData>>()

    fun setDummyHeartData(measureHRList: ArrayList<HeartRateData>): ArrayList<Float> {
        var heartDataList = ArrayList<Float>()
        if(measureHRList.isNotEmpty()) {
            measureHRList.forEach {
                heartDataList.add(it.heartRateBpm?.toFloat() ?: 0.1f)
            }
        }else
            heartDataList.add(0.1f)

        return heartDataList
    }

    suspend fun insertData(heartRateData: List<HeartRateData>) {
        CoroutineScope(Dispatchers.IO).launch {
            heartRateData.map {
                getHeartRateUseCase.insertAllHeartRateData(heartRateData)
            }

        }
    }

}