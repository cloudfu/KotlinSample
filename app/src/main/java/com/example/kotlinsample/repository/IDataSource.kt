package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.entity.mvvmUseCases
import kotlinx.coroutines.flow.Flow

interface IDataSource {

    fun helloWorld(): String
    suspend fun fetchUseCaseCategories(pageIndex: Int): Flow<List<UseCaseCategory>>
}