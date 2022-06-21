package com.example.wearableapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.domain.model.HeartRateData
import com.example.domain.usecase.GetHeartRateOperationsUseCase
import kotlinx.coroutines.*

class MeasureHeartRateViewModel(private val getHeartRateOperationsUseCase :GetHeartRateOperationsUseCase): ViewModel() {

    var heartLiveData = MutableLiveData<List<HeartRateData>>()

    fun setDummyHeartData(measureHRList: List<HeartRateData>?): ArrayList<Float> {
        var heartDataList = ArrayList<Float>()
        //heartDataList.clear()
        if(!measureHRList.isNullOrEmpty()) {
            measureHRList.forEach {
                heartDataList.add(it.heartRateBpm?.toFloat() ?: 0.1f)
            }
        }else {
            heartDataList.add(0.1f)
        }
        return heartDataList
    }

    suspend fun insertData(heartRateData: List<HeartRateData>) {
        CoroutineScope(Dispatchers.IO).launch {
            heartRateData.map {
                getHeartRateOperationsUseCase.insertAllHeartRateData(heartRateData)
            }

        }
    }

    fun getSavedData(): LiveData<List<HeartRateData>> {
        heartLiveData.value =getHeartRateOperationsUseCase.getAllHeartListData().asLiveData().value
        return getHeartRateOperationsUseCase.getAllHeartListData().asLiveData()}



}