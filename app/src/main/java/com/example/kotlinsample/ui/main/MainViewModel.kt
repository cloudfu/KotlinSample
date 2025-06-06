package com.example.kotlinsample.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotlinsample.repository.DataRepository
import com.example.model.data.ApiResponse
import com.example.model.vm.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.transform
import timber.log.Timber

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
): BaseViewModel() {

    private val TAG = "MainViewModel"

    private val pageIndex = MutableStateFlow<Int>(0)
    val useCaseCategories = MutableLiveData<List<UseCase>>(emptyList())

    val flowData = pageIndex.transform<Int,String> {
        emit("pageIndex: $it")
    }

    /***
     * 进行数据列表翻页
     */
    fun fetchNextPage() {
        pageIndex.value++
        Timber.d(TAG)
        viewModelScope.launch {
            dataRepository.fetchUseCaseCategories(pageIndex.value).collect{
                when(it){
                    is ApiResponse.Loading -> {
                        Timber.d(TAG, "loading")
                        loading.value = true
                    }

                    is ApiResponse.Success<*> -> {
                        useCaseCategories.value = it.data as List<UseCase>
                    }

                    is ApiResponse.Failure -> {
//                        Timber.d(TAG, "fetchUseCaseCategories: ${it.state.msg}")
//                        useCaseCategories.value = it
                    }
                }
            }
        }
    }

    private fun MutableLiveData<Int>.increment() {
        this.value = this.value?.plus(1) ?: 1
    }

}