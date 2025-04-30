package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.entity.mvvmUseCases
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val mockDataSource: MockDataSource
): IDataSource{

    override fun helloWorld(): String{
        return mockDataSource.helloWorld()
    }

    override fun fetchUseCaseCategories(pageIndex: Int): List<UseCaseCategory> {
        return mockDataSource.fetchUseCaseCategories(pageIndex)
    }
}