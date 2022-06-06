package com.oliferov.usdrateapp.domain

class GetUsdRateForYesterdayUseCase(private val repository: RepositoryUsdRate) {
    suspend operator fun invoke() = repository.getUsdRateForYesterday()
}