package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.entity.mvvmUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber
import javax.inject.Inject

class MockDataSource @Inject constructor(): IDataSource {

    private val TAG = "MockDataSource"

    override fun helloWorld(): String{
        return "Hello World"
    }

    override suspend fun fetchUseCaseCategories(pageIndex: Int): Flow<List<UseCaseCategory>> = flow {
        emit(buildList<UseCaseCategory> {
            for (i in 1..pageIndex * 10) {
                add(UseCaseCategory("Mock Item $i", mvvmUseCases))
            }
        })
    }
        .onStart { Timber.d("onStart")}
        .onCompletion { Timber.d("onCompletion")}
        .onEach { Timber.d(it.toString()) }
        .flowOn(Dispatchers.IO)
}