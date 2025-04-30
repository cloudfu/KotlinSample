package com.example.kotlinsample.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.Transformations

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
): ViewModel() {

    private val TAG = "MainViewModel"

    private val pageIndex = MutableLiveData<Int>(3)
    private val useCaseCategories = MutableLiveData<List<UseCaseCategory>>()

    /***
     * 进行数据列表翻页
     */
    fun fetchNextPage(): List<UseCaseCategory>{
        pageIndex.increment()
        val currentPage = pageIndex.value ?: 1
        viewModelScope.launch {

        }
        return dataRepository.fetchUseCaseCategories(currentPage)
    }

    val sourceLiveData: LiveData<Int> = MutableLiveData(10)
    val transformedLiveData: LiveData<String> = Transformations.map(sourceLiveData) { num ->
        "Value is: $num" // 将 Int 转换为 String
    }

    private fun MutableLiveData<Int>.increment() {
        this.value = this.value?.plus(1) ?: 1
    }

}