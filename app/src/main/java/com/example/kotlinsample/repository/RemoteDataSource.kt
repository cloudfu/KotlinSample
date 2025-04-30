package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.entity.mvvmUseCases
import com.example.model.adapter.NetworkProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val networkProvider: NetworkProvider
): IDataSource {

    override fun helloWorld(): String{
        return "Hello World"
    }

    override fun fetchUseCaseCategories(pageIndex: Int): List<UseCaseCategory> {
        return buildList<UseCaseCategory> {
            for (i in 1..pageIndex*10) {
                add(UseCaseCategory("Mock Item $i", mvvmUseCases))
            }
        }
    }
}