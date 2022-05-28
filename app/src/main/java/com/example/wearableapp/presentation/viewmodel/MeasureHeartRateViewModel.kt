package com.example.wearableapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.data.network.model.HeartRate
import com.example.domain.model.HeartRateInfo
import com.example.domain.model.onFailure
import com.example.domain.model.onSuccess
import com.example.domain.usecase.GetHeartRateUseCase
import com.example.wearableapp.presentation.base.BaseViewModel
import com.example.wearableapp.presentation.base.Success
import com.example.wearableapp.presentation.base.Error
import com.example.wearableapp.presentation.utils.Constants.Companion.DEFAULT_NAME

class MeasureHeartRateViewModel(private val getHeartRateUseCase: GetHeartRateUseCase) : BaseViewModel<HeartRateInfo,Any>() {

    var heartLiveData = MutableLiveData<HeartRate>()

    fun sendHeartRateData(userName:String = DEFAULT_NAME) = executeUseCase{
        getHeartRateUseCase(userName)
            .onSuccess {  _viewState.value = Success(it)  }
            .onFailure { _viewState.value = Error(it.throwable)  }

    }
}