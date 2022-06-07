package com.oliferov.usdrateapp.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.oliferov.usdrateapp.data.data.UsdRateDao
import com.oliferov.usdrateapp.data.data.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.data.network.dto.UsdRateDto
import com.oliferov.usdrateapp.domain.RepositoryUsdRate
import com.oliferov.usdrateapp.domain.UsdRate
import javax.inject.Inject
import kotlin.reflect.typeOf


class RepositoryUsdRateImpl @Inject constructor(
    private val apiService: UsdRateApiService,
    private val application: Application,
    private val mapperUsdRate: MapperUsdRate,
    private val usdRateDao: UsdRateDao
) : RepositoryUsdRate {

    override suspend fun getUsdRateForTodayAndYesterday(yesterday: Int, today: Int) =
        mapperUsdRate
            .mapUsdRateDbModelListToUsdRateList(
                usdRateDao.getUsdRateForTodayAndYesterday(yesterday, today)
            )


    override suspend fun getUsdRatePerMonth(
        dateBeginningMonth: String,
        dateEndMonth: String
    ): List<UsdRate> {
        if (isConnected()) {
            val listDto = loadData(dateBeginningMonth, dateEndMonth)
            Log.d("DXD", " LOL in if")
            if (listDto.isNotEmpty()) {
                usdRateDao.deleteAllUsdRate()
                usdRateDao.insertAllUsdRate(
                    mapperUsdRate
                        .mapUsdRateDtoListToUsdRateDbModelList(listDto)
                )
            }
        } else {
            Log.d("DXD", " LOL in else")
        }
        return mapperUsdRate
            .mapUsdRateDbModelListToUsdRateList(
                usdRateDao.getAllUsdRate()
            )
    }

    private suspend fun loadData(dateBeginningMonth: String, dateEndMonth: String) =
        apiService
            .getUsdRatePerMonth(dateBeginningMonth, dateEndMonth)
            .body()
            ?.arrayDollar
            ?: emptyList<UsdRateDto>()

    private suspend fun isConnected(): Boolean {
        var result = false
        val cM =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
}

//        val activeNetwork = cM.activeNetworkInfo
//        if (cM.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnectedOrConnecting!! ||
//            cM.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnectedOrConnecting!!) {

//        if (activeNetwork != null && (
//                    activeNetwork.type == ConnectivityManager.TYPE_WIFI ||
//                            activeNetwork.type == ConnectivityManager.TYPE_MOBILE
//                    )
//        ) {

// -------------------------------------------------------------------------------

//        val connectivityManager =
//            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        var hasConnected = false
//        Log.d("DXD", hasConnected.toString() + " LOL")
//        connectivityManager.registerNetworkCallback(
//            NetworkRequest.Builder().addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
//                .build(),
//            object : ConnectivityManager.NetworkCallback() {
//                override fun onAvailable(network: Network) {
//                    super.onAvailable(network)
//                    hasConnected = true
//                    Log.d("DXD", hasConnected.toString() + " True")
//                }
//
//                override fun onLost(network: Network) {
//                    super.onLost(network)
//                    hasConnected = false
//                    Log.d("DXD", hasConnected.toString() + " True")
//                }
//            }
//        )
//        return hasConnected