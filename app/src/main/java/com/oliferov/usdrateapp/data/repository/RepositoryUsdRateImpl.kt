package com.oliferov.usdrateapp.data.repository

import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.domain.RepositoryUsdRate
import com.oliferov.usdrateapp.domain.UsdRate
import javax.inject.Inject


class RepositoryUsdRateImpl @Inject constructor(private val apiService: UsdRateApiService): RepositoryUsdRate {
    override suspend fun getUsdRateForToday(): UsdRate {
        TODO("Not yet implemented")
    }

    override suspend fun getUsdRateForYesterday(): UsdRate {
        TODO("Not yet implemented")
    }

    override suspend fun getUsdRatePerMonth(dateBeginningMonth: String, dateEndMonth: String): List<UsdRate> {
        loadData(dateBeginningMonth,dateEndMonth)
        TODO()
    }

    suspend fun loadData(dateBeginningMonth: String, dateEndMonth: String){
        apiService.getUsdRatePerMonth(dateBeginningMonth,dateEndMonth)
    }
}