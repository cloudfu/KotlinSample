package com.example.kotlinsample.repository

import com.example.kotlinsample.entity.UseCaseCategory
import com.example.kotlinsample.entity.mvvmUseCases

interface IDataSource {

    fun helloWorld(): String
    fun fetchUseCaseCategories(pageIndex: Int): List<UseCaseCategory>
}