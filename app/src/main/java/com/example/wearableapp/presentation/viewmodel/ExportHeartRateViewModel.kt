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

class ExportHeartRateViewModel(private val getHeartRateUseCase : GetHeartRateDataUseCase) : ViewModel() {

    val TAG = ExportHeartRateViewModel::class.simpleName
    var heartRateData = MutableLiveData<List<HeartRateData>>()
    var exportStatus = MutableLiveData<String>()

    fun getAllHearRateData(): LiveData<List<HeartRateData>> {
        return getHeartRateUseCase.getAllHeartListData().asLiveData()
    }

    fun startDataExporting(heartRateData: List<HeartRateData>?) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "" + heartRateData?.size + "\n" + heartRateData?.get(0)?.name)
            if (heartRateData != null) {
                exportStatus.postValue(Constants.Status.STARTED.name)
                val result = getHeartRateUseCase.createCSVDataFormat(heartRateData)
                Log.d(TAG, "Result-" + result)
                if (result) {
                    deleteHeartRateData()
                    exportStatus.postValue(Constants.Status.SUCCESSED.name)
                }else
                    exportStatus.postValue(Constants.Status.FAILED.name)
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