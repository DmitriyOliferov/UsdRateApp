package com.oliferov.usdrateapp.domain

interface RepositoryUsdRate {
    fun getUsdRateForToday(): UsdRate

    fun getUsdRateForYesterday(): UsdRate

    fun getUsdRatePerMonth(): List<UsdRate>
}