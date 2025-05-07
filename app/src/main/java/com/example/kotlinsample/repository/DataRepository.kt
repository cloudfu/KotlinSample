package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.entity.mvvmUseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val mockDataSource: MockDataSource
){

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    fun helloWorld(): String{
        return mockDataSource.helloWorld()
    }

    suspend fun fetchUseCaseCategories(pageIndex: Int): Flow<List<UseCaseCategory>> =
        withContext(defaultDispatcher){
            return@withContext mockDataSource.fetchUseCaseCategories(pageIndex)
    }
}