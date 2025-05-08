package com.example.kotlinsample.ui.main

import android.os.Parcelable
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

