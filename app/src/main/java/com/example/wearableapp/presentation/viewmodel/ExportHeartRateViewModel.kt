package com.example.wearableapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.domain.model.HeartRateData
import com.example.domain.usecase.GetHeartRateDataUseCase
import com.example.wearableapp.presentation.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ExportHeartRateViewModel() : ViewModel() {

    var heartRateData = MutableLiveData<List<HeartRateData>>()
    var exportStatus = MutableLiveData<String>()

    fun getAllHearRateData(): LiveData<List<HeartRateData>> {
        val list = ArrayList<HeartRateData>()
        for(i in 1..4){
            list.add(HeartRateData(1.0+i,Date().time,Constants.DEFAULT_NAME))
        }
        heartRateData.value = list
        return heartRateData
    }

    fun startDataExporting(heartRateData: List<HeartRateData>?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (heartRateData != null) {
                exportStatus.postValue("Started")
                if (true) {
                    exportStatus.postValue("Success")
                }else
                    exportStatus.postValue("Failed")
            }
        }
    }


}