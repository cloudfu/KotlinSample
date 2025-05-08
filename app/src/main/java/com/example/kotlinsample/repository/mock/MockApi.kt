package com.example.kotlinsample.repository.mock

import com.example.kotlinsample.ui.main.UseCase
import com.example.model.providers.network.MockNetworkInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RemoteApi {

    @GET("fetch-use-case-categories/")
    suspend fun fetchUseCaseCategories(): List<UseCase>
}

fun createMockApi(interceptor: MockNetworkInterceptor): RemoteApi {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(RemoteApi::class.java)
}