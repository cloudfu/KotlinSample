package com.example.kotlinsample.repository.local

import com.example.kotlinsample.entity.UseCase
import com.example.kotlinsample.repository.IDataSource
import com.example.model.data.ApiResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
): IDataSource {

    override fun helloWorld(): String{
        return "Hello World"
    }

    override suspend fun fetchUseCaseCategories(pageIndex: Int): ApiResponse {
        TODO("Not yet implemented")
    }

}