package com.oliferov.usdrateapp.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.oliferov.usdrateapp.data.data.UsdRateDao
import com.oliferov.usdrateapp.data.data.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.data.network.dto.UsdRateDto
import com.oliferov.usdrateapp.domain.RepositoryUsdRate
import com.oliferov.usdrateapp.domain.UsdRate
import com.oliferov.usdrateapp.notifications.UsdRateWorker
import com.oliferov.usdrateapp.source.isConnected
import com.oliferov.usdrateapp.source.loadData
import javax.inject.Inject


class RepositoryUsdRateImpl @Inject constructor(
    private val apiService: UsdRateApiService,
    private val application: Application,
    private val mapperUsdRate: MapperUsdRate,
    private val usdRateDao: UsdRateDao
) : RepositoryUsdRate {

    override fun addNotificationUsdRate(rub: String) {
        WorkManager.getInstance(application).apply {
            enqueueUniquePeriodicWork(
                UsdRateWorker.NAME_WORK,
                ExistingPeriodicWorkPolicy.REPLACE,
                UsdRateWorker.makeRequest(rub)
            )
        }
    }


    override suspend fun getUsdRatePerMonth(): List<UsdRate> {
        Log.d("DXD", " LOL in ")
        if (isConnected(application)) {
            loadData(apiService, usdRateDao, mapperUsdRate)
            Log.d("DXD", " LOL in if")
        } else {
            Log.d("DXD", " LOL in else")
        }
        Log.d("DXD", " LOL out ")
        return mapperUsdRate
            .mapUsdRateDbModelListToUsdRateList(
                usdRateDao.getAllUsdRate().reversed()
            )
    }
}

