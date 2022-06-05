package com.example.wearableapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.domain.model.HeartRateData
import com.example.domain.usecase.GetHeartRateDataUseCase
import kotlinx.coroutines.*

class MeasureHeartRateViewModel(): ViewModel() {

    var heartLiveData = MutableLiveData<List<HeartRateData>>()

    fun setDummyHeartData(measureHRList: ArrayList<HeartRateData>): ArrayList<Float> {
        var heartDataList = ArrayList<Float>()
        if(measureHRList.isNotEmpty()) {
            measureHRList.forEach {
                heartDataList.add(it.heartRateBpm?.toFloat() ?: 0.1f)
            }
        }else
            for(i in 0..9)
            heartDataList.add(0.1f+i)

        return heartDataList
    }



}