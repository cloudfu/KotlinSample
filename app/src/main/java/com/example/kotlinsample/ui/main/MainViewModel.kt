package com.example.kotlinsample.ui.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.kotlinsample.data.UseCaseCategory
import com.example.kotlinsample.data.mvvmUseCases
import com.example.model.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    private val TAG = "MainViewModel"

    private val pageIndex = MutableLiveData<Int>(3)
    private val useCaseCategories = MutableLiveData<List<UseCaseCategory>>()

//    val outputLiveData: LiveData<String> = Transformations.switchMap(inputLiveData) { input ->
//        getResultLiveData(input)
//    }
//    private val useCaseCategories: LiveData<List<UseCaseCategory>> = Transformations.switchMap(pageIndex) { pageIndex ->
//        fetchUseCaseCategories(pageIndex)
//    }

//    init{
//        pageIndex.observeForever(Observer { pageIndex ->
//            fetchUseCaseCategories(pageIndex)
//        })
//    }

    /***
     * 进行数据列表翻页
     */
    fun loadNextPage(): List<UseCaseCategory>?{
        pageIndex.value?.plus(1)?:0
        useCaseCategories.value = fetchUseCaseCategories(pageIndex.value!!)
        return useCaseCategories.value
    }

    private fun fetchUseCaseCategories(pageIndex: Int): List<UseCaseCategory>{
        return buildList<UseCaseCategory> {
            for (i in 1..pageIndex*10) {
                add(UseCaseCategory("Mock$i", mvvmUseCases))
            }
        }
    }
}