package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.entity.mvvmUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val mockDataSource: MockDataSource
){

    fun helloWorld(): String{
        return mockDataSource.helloWorld()
    }

    suspend fun fetchUseCaseCategories(pageIndex: Int): Flow<List<UseCaseCategory>>{
        return mockDataSource.fetchUseCaseCategories(pageIndex)
    }
}