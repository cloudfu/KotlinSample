package com.example.kotlinsample.repository

import com.example.kotlinsample.repository.mock.MockDataSource
import com.example.model.data.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
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
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val dataSource: MockDataSource
){

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    fun helloWorld(): String{
        return dataSource.helloWorld()
    }

    fun fetchUseCaseCategories(pageIndex: Int): Flow<ApiResponse> {
        return flow {
            emit(dataSource.fetchUseCaseCategories(pageIndex))
        }.onStart { Timber.d("onStart")}
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

    // TODO：需要监听数据库表变化（如Room返回Flow<List<User>>）
    // 合并多个数据源（如本地缓存 + 网络请求）并持续更新
    // 处理事件流（如点击防抖、实时搜索）
}