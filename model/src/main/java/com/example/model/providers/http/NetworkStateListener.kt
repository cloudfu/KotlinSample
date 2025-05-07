package com.example.model.providers.http

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

sealed class NetworkState {
    data object Disconnected : NetworkState()
    data object Available : NetworkState()
    data object WifiConnected : NetworkState()
    data object MobileConnected : NetworkState()
    data object EthernetConnected : NetworkState()
    data object Unknown : NetworkState()
    data object Error : NetworkState()
}

@Singleton
class NetworkStateListener @Inject constructor(@ApplicationContext val context: Context) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _networkState.postValue(NetworkState.Available)
        }
        override fun onLost(network: Network) {
            _networkState.postValue(NetworkState.Disconnected)
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    // WiFi连接
                    _networkState.postValue(NetworkState.WifiConnected)
                }
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    // 移动数据连接
                    _networkState.postValue(NetworkState.MobileConnected)
                }
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    // 有线网络连接
                    _networkState.postValue(NetworkState.EthernetConnected)
                }
                else -> {
                    // 其他连接类型
                    _networkState.postValue(NetworkState.Unknown)
                }
            }
        }

        override fun onUnavailable() {
            _networkState.postValue(NetworkState.Error)
        }
    }

    fun start() {
        // API 21+ 推荐方式
        // defaultConfig { minSdk = 24  }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            // 旧版本使用广播方案
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            connectivityManager.registerNetworkCallback(request, networkCallback)
        }
    }

    fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    fun getNetworkState(): NetworkState {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkNewApiNetworkState()
        } else {
            checkLegacyNetworkState()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkNewApiNetworkState(): NetworkState {
        val activeNetwork = connectivityManager.activeNetwork ?: return NetworkState.Disconnected
        val caps = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return NetworkState.Disconnected

        return when {
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkState.WifiConnected
            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkState.MobileConnected
            else -> NetworkState.Unknown
        }
    }

    @Suppress("DEPRECATION")
    private fun checkLegacyNetworkState(): NetworkState {
        val activeNetwork = connectivityManager.activeNetworkInfo ?: return NetworkState.Disconnected
        return when {
            !activeNetwork.isConnectedOrConnecting -> NetworkState.Disconnected
            activeNetwork.type == ConnectivityManager.TYPE_WIFI -> NetworkState.WifiConnected
            activeNetwork.type == ConnectivityManager.TYPE_MOBILE -> NetworkState.MobileConnected
            else -> NetworkState.Unknown
        }
    }
}
