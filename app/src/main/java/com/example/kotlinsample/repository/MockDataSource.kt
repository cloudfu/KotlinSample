package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.entity.mvvmUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
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
        // 通过cause和attempt 进行 retry控制
        .retryWhen { cause, attempt ->
            cause.message == "test exception" && attempt < 2
        }
        .catch { ex ->
            println("catch exception: ${ex.message}")
        }
        // onCompletion 无论是否有Exception都会有调用
        .onCompletion { cause ->
            if (cause != null) {
                println("flow completed exception")
            } else {
                println("onCompletion")
            }
        }
        .onEach { Timber.d(it.toString()) }
        .flowOn(Dispatchers.IO)
}