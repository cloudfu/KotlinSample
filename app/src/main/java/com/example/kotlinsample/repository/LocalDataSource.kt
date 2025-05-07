package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCaseCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
): IDataSource {

    override fun helloWorld(): String{
        return "Hello World"
    }

    override suspend fun fetchUseCaseCategories(pageIndex: Int): Flow<List<UseCaseCategory>> {
        TODO("Not yet implemented")
    }

}