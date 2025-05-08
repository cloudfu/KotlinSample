package com.example.model.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.model.data.ResultState

open class BaseViewModel : ViewModel() {

    // TODO: 想办法将ResultData和uiState进行结合，数据上下游打通
    // TODO: Loading timeout集成
    val loading: MutableLiveData<Boolean> = MutableLiveData()
}