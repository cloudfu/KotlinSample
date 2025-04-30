package com.example.kotlinsample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
//import androidx.lifecycle.Transformations

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
): ViewModel() {

    private val TAG = "MainViewModel"

    private val pageIndex = MutableLiveData<Int>(0)
    val useCaseCategories: LiveData<List<UseCaseCategory>> = pageIndex.map {page ->
        dataRepository.fetchUseCaseCategories(page)
    }

    /***
     * 进行数据列表翻页
     */
    fun fetchNextPage(){
        pageIndex.nextPage()
    }

    private fun MutableLiveData<Int>.nextPage() {
        this.value = this.value?.plus(1) ?: 1
    }

}