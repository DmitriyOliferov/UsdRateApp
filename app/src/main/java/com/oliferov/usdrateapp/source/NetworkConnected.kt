package com.oliferov.usdrateapp.source

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

fun isConnected(context: Context): Boolean {
    var result = false
    val cM =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    cM.run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cM.getNetworkCapabilities(cM.activeNetwork)?.run {
                if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                ) {
                    result = true
                }
            }
        } else {
            cM.run {
                cM.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI ||
                        type == ConnectivityManager.TYPE_MOBILE ||
                        type == ConnectivityManager.TYPE_VPN
                    ) {
                        result = true
                    }
                }
            }
        }
    }
    return result
}