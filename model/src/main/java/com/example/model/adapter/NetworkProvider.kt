package com.example.model.adapter

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkProvider @Inject constructor(
    @ApplicationContext val context: Context
){
}