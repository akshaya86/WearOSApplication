package com.example.wearableapp.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.coroutine.CoroutineContextProvider
import com.example.wearableapp.presentation.extensions.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<T : Any, E>  : ViewModel(), KoinComponent {
    protected val coroutineContext: CoroutineContextProvider by inject()

    protected val _viewState = MutableLiveData<ViewState<T>>()
    val viewState: LiveData<ViewState<T>>
        get() = _viewState

    protected val _viewEffects = MutableLiveData<E>()
    val viewEffects: LiveData<E>
        get() = _viewEffects

    protected fun executeUseCase(action: suspend () -> Unit, noInternetAction: () -> Unit) {
        _viewState.value = Loading()
            launch { action() }
    }

    protected fun executeUseCase(action: suspend () -> Unit) {
        _viewState.value = Loading()
        launch { action() }
    }

}
