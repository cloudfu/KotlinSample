package com.example.kotlinsample.entity

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinsample.usecase.repo.network.NetworkActivity
import com.example.kotlinsample.usecase.repo.room.RoomActivity
import kotlinx.parcelize.Parcelize



@Parcelize
data class UseCase(
    val name: String,
    val description: String = ""
) : Parcelable

/***
 * MVVM UserCases
 */
val userCaseList = listOf(
    UseCase("Network"),
    UseCase("Room")
)

