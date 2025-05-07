package com.example.kotlinsample.api

import com.example.kotlinsample.entity.UseCaseCategory
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteApi {

    @GET("use_case_categories")
    suspend fun fetchUseCaseCategories(): List<UseCaseCategory>
}