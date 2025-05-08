package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCase
import com.example.model.data.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IDataSource {

    fun helloWorld(): String
    suspend fun fetchUseCaseCategories(pageIndex: Int): ApiResponse

}