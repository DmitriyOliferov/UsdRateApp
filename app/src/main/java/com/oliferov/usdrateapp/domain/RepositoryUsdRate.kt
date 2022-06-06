package com.oliferov.usdrateapp.domain

interface RepositoryUsdRate {
    suspend fun getUsdRateForToday(): UsdRate

    suspend fun getUsdRateForYesterday(): UsdRate

    suspend fun getUsdRatePerMonth(dateBeginningMonth: String, dateEndMonth: String): List<UsdRate>
}