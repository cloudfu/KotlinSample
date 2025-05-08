package com.example.kotlinsample.repository.mock

import com.example.kotlinsample.ui.main.UseCase
import com.example.kotlinsample.repository.IDataSource
import com.example.model.data.ApiResponse
import com.example.model.providers.network.MockNetworkInterceptor
import com.google.gson.Gson
import com.example.model.data.ErrorCodes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockDataSource @Inject constructor(): IDataSource {

    private val TAG = "MockDataSource"

    override fun helloWorld(): String{
        return "Hello World"
    }

    override suspend fun fetchUseCaseCategories(pageIndex: Int): ApiResponse {
        val interceptor = MockNetworkInterceptor()
            .mock(
                "http://localhost/fetch-use-case-categories/",
                { Gson().toJson(getUseCaseList(30)) },
                ErrorCodes.SUCCEED.code,
                1500
            )

        // TODO:应该需要通过ResponseBody进行code判断
        return try {
            ApiResponse.Success(createMockApi(interceptor).fetchUseCaseCategories())
        } catch (ex: Exception) {
            ApiResponse.Failure(ErrorCodes.TIMEOUT)
        }
    }

//    suspend fun fetchUseCaseCategories1(pageIndex: Int): Flow<List<UseCase>> {
//        val interceptor = MockNetworkInterceptor()
//            .mock(
//                "http://localhost/fetch-use-case-categories/",
//                { Gson().toJson(getUseCaseList(30)) },
//                ResultState.SUCCEED.code,
//                1500
//            )
//
//
//        try{
//            val response = ResultPackage.Success(createMockApi(interceptor).fetchUseCaseCategories())
//            val result = ResultPackage.Success(response)
//
//        }catch (ex: Exception){
//            return ResultPackage.Failure(1,ex.message.toString())
//        }
//        return MutableStateFlow(result)
//    }

    private fun getUseCaseList(size: Int): List<UseCase> {
        return buildList<UseCase> {
            for (i in 1..size) {
                add(UseCase("Mock Item $i"))
            }
        }
    }
}