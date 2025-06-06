package com.example.model.adapter

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataProvider @Inject constructor(
    @ApplicationContext private val context: Context
) {
}