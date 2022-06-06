package com.oliferov.usdrateapp.domain

class GetUsdRateForYesterdayUseCase(private val repository: RepositoryUsdRate) {
    operator fun invoke() = repository.getUsdRateForYesterday()
}