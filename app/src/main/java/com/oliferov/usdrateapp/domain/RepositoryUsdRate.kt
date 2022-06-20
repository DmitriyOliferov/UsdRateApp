package com.oliferov.usdrateapp.domain

interface RepositoryUsdRate {
    fun addNotificationUsdRate(rub: String)

    suspend fun getUsdRatePerMonth(): List<UsdRate>
}