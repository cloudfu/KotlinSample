package com.example.kotlinsample.entity

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinsample.usecase.repo.network.NetworkActivity
import com.example.kotlinsample.usecase.repo.room.RoomActivity
import kotlinx.parcelize.Parcelize



@Parcelize
data class UseCase(
    val description: String,
    val targetActivity: Class<out AppCompatActivity>
) : Parcelable

@Parcelize
data class UseCaseCategory(val categoryName: String, val useCases: List<UseCase>) : Parcelable

/***
 * MVVM UserCases
 */
val mvvmUseCases = listOf(
    UseCase("Network", NetworkActivity::class.java),
    UseCase("Room", RoomActivity::class.java)
)

/***
 * UseCase Category
 */
val userCaseCategories = listOf(
    UseCaseCategory("MVVM", mvvmUseCases),
    UseCaseCategory("Placeholder", mvvmUseCases),
    UseCaseCategory("Placeholder", mvvmUseCases),
    UseCaseCategory("Placeholder", mvvmUseCases)
)