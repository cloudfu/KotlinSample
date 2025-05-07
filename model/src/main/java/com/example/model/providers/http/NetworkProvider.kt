package com.example.model.providers.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkProvider @Inject constructor(@ApplicationContext val context: ApplicationContext) {

    private val HTTP_HOST_URL = "https://pokeapi.co/api/v2/"
    private val HTTP_CONNECT_TIMEOUT = 20
    private val mOkHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    private lateinit var mRetrofit: Retrofit

    // Http Logger
    private val mHttpLogger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            return loggingInterceptor
        }

    // Http Head
    private var mHttpHeader = Interceptor { chain ->
        val original = chain.request()
        val request = original.newBuilder()
            .header("Content-Type", "application/json")
            .method(original.method, original.body)
            .build()
        chain.proceed(request)
    }

    init{
        // Proxy
//        val proxyAddress = InetSocketAddress("127.0.0.1", 7897)
//        val proxy = java.net.Proxy(java.net.Proxy.Type.HTTP, proxyAddress)
//        okHttpBuilder.proxy(proxy)

        mOkHttpClient.connectTimeout(20, TimeUnit.SECONDS)
        mOkHttpClient.addInterceptor(mHttpLogger)
        mOkHttpClient.addInterceptor(mHttpHeader)
//        mOkHttpClient.addInterceptor(dynamicHostUri)
        mOkHttpClient.connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        mOkHttpClient.readTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        mOkHttpClient.writeTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)

        mRetrofit = Retrofit.Builder()
            .client(mOkHttpClient.build())
            .baseUrl(HTTP_HOST_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .addConverterFactory(MoshiConverterFactory.create())
//            .addConverterFactory(FormUrlEncodedConverterFactory())
//            .addConverterFactory(DynamicConverterFactory())
            .build()
    }
}
