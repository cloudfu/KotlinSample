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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
): ViewModel() {

    private val TAG = "MainViewModel"

    private val pageIndex = MutableStateFlow<Int>(0)
    val useCaseCategories = MutableLiveData<List<UseCaseCategory>>(emptyList())

    /***
     * 进行数据列表翻页
     */
    fun fetchNextPage() {
        pageIndex.value++
        Timber.d(TAG)
        viewModelScope.launch {
            dataRepository.fetchUseCaseCategories(pageIndex.value).collect{
                useCaseCategories.value = it
            }
        }
    }

    private fun MutableLiveData<Int>.increment() {
        this.value = this.value?.plus(1) ?: 1
    }

}