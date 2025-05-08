package com.example.kotlinsample.repository

import com.example.model.data.ApiResponse

interface IDataSource {

    fun helloWorld(): String
    suspend fun fetchUseCaseCategories(pageIndex: Int): ApiResponse

}