package com.example.wearableapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.data.network.model.HeartRate
import com.example.domain.model.HeartRateInfo
import com.example.domain.model.onFailure
import com.example.domain.model.onSuccess
import com.example.domain.usecase.IGetHeartRateUseCase
import com.example.wearableapp.presentation.base.BaseViewModel
import com.example.wearableapp.presentation.base.Success
import com.example.wearableapp.presentation.base.Error
import com.example.wearableapp.presentation.utils.Constants.Companion.DEFAULT_NAME

class MeasureHeartRateViewModel(private val getHeartRateUseCase: IGetHeartRateUseCase) : BaseViewModel<HeartRateInfo,Any>() {

    var heartLiveData = MutableLiveData<HeartRate>()

    fun sendHeartRateData(userName:String = DEFAULT_NAME) = executeUseCase{
        getHeartRateUseCase(userName)
            .onSuccess {  _viewState.value = Success(it)  }
            .onFailure { _viewState.value = Error(it.throwable)  }

    }

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
}