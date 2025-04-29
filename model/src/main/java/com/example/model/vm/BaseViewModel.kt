package com.example.model.vm

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    private val lock: Any = Any()
}