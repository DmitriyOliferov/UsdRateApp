package com.oliferov.usdrateapp.data.repository

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.oliferov.usdrateapp.data.database.UsdRateDao
import com.oliferov.usdrateapp.data.database.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
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
        if (isConnected(application)) {
            loadData(apiService, usdRateDao, mapperUsdRate)
        }
        return mapperUsdRate
            .mapUsdRateDbModelListToUsdRateList(
                usdRateDao.getAllUsdRate().reversed()
            )
    }
}

