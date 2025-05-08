package com.example.kotlinsample.repository.remote

import com.example.kotlinsample.ui.main.UseCase
import com.example.kotlinsample.repository.IDataSource
import com.example.model.adapter.NetworkProvider
import com.example.model.data.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val networkProvider: NetworkProvider
): IDataSource {

    override fun helloWorld(): String{
        return "Hello World"
    }

    override suspend fun fetchUseCaseCategories(pageIndex: Int): ApiResponse {
        TODO("Not yet implemented")
    }

    suspend fun fetchUseCaseCategories1(pageIndex: Int): Flow<List<UseCase>> = flow {
        delay(1000)
        emit(buildList<UseCase> {
            for (i in 1..pageIndex * 10) {
                add(UseCase("Mock Item $i"))
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