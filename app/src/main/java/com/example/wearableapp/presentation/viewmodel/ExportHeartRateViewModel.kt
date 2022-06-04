package com.example.wearableapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.domain.model.HeartRateData
import com.example.domain.usecase.GetHeartRateDataUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*

class ExportHeartRateViewModel(private val getHeartRateUseCase : GetHeartRateDataUseCase) : ViewModel() {

    var heartRateData = MutableLiveData<List<HeartRateData>>()
    var exportStatus = MutableLiveData<String>()

    fun getAllHearRateData(): LiveData<List<HeartRateData>> {
        return getHeartRateUseCase.getAllHeartListData().asLiveData()
    }

    fun startDataExporting(heartRateData: List<HeartRateData>?) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ex-data", "" + heartRateData?.size + "\n" + heartRateData?.get(0)?.name)
            if (heartRateData != null) {
                exportStatus.postValue("Started")
                val result = getHeartRateUseCase.createCSVDataFormat(heartRateData)
                Log.d("ex-data", "Result-" + result)
                if (result) {
                    delay(2000)
                    deleteHeartRateData()
                    exportStatus.postValue("Success")
                }else
                    exportStatus.postValue("Failed")
            }
        }
    }

    fun deleteHeartRateData(): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            getHeartRateUseCase.deleteHeartRateData()
        }
        return true
    }


}