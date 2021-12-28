package com.example.koin.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtility {


    @Suppress("DEPRECATION")
    fun isConnected(context: Context): Boolean {

        var isConnected = false

        val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nwk = connMgr.activeNetwork ?: return false
            val nwkCap = connMgr.getNetworkCapabilities(nwk) ?: return false
            isConnected = when {
                nwkCap.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                nwkCap.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                nwkCap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        } else {
            connMgr.run {
                activeNetworkInfo?.run {
                    isConnected = if (type == ConnectivityManager.TYPE_WIFI) true
                    else type == ConnectivityManager.TYPE_MOBILE
                }
            }

        }
        return isConnected

    }


}