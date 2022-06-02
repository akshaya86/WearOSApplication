package com.example.wearableapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.data.database.entity.HeartRateEntity
import com.example.domain.model.HeartRateData
import com.example.domain.usecase.GetHeartRateDataUseCase

class MeasureHeartRateViewModel(private val getHeartRateUseCase :GetHeartRateDataUseCase): ViewModel() {

    var heartLiveData = MutableLiveData<HeartRateEntity>()

    fun setDummyHeartData(): ArrayList<Float> {
        var heartDataList = ArrayList<Float>()
        heartDataList.add(1.0F);heartDataList.add(4.2F)
        heartDataList.add(3.4F);heartDataList.add(1.4F)
        heartDataList.add(6.8F);heartDataList.add(2.0F)
        heartDataList.add(4.2F);heartDataList.add(8.4F)
        heartDataList.add(3.4F);heartDataList.add(1.6F)
        heartDataList.add(5.2F);heartDataList.add(3.3F)
        heartDataList.add(4.8F);heartDataList.add(2.9F)
        heartDataList.add(1.0F);heartDataList.add(4.2F)
        heartDataList.add(3.4F);heartDataList.add(1.4F)
        heartDataList.add(6.8F);heartDataList.add(2.0F)
        heartDataList.add(4.2F);heartDataList.add(8.4F)
        heartDataList.add(3.4F);heartDataList.add(1.6F)
        heartDataList.add(5.2F);heartDataList.add(3.3F)
        heartDataList.add(4.8F);heartDataList.add(2.9F)
        return heartDataList
    }

    suspend fun getSavedData(): LiveData<List<HeartRateData>> {
        val data = getHeartRateUseCase.getAllHeartListData().asLiveData()
        return data
    }

    suspend fun insertData(){
        getHeartRateUseCase.insertHeartRateData(HeartRateData(40.0,100000,"Ak"))
    }

}