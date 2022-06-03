package com.example.wearableapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.model.HeartRateData
import com.example.domain.usecase.GetHeartRateDataUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ExportHeartRateViewModel(private val getHeartRateUseCase : GetHeartRateDataUseCase) : ViewModel() {

    suspend fun insertData(){
        getHeartRateUseCase.insertHeartRateData(HeartRateData(40.0, 100000, "Ak"))
    }

    fun getAllHearRateData(): LiveData<List<HeartRateData>> {
        return getHeartRateUseCase.getAllHeartListData().asLiveData()
    }

    fun startDataExporting(){
        viewModelScope.launch {
            val data = getAllHearRateData()

        }
    }


}