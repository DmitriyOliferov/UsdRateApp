package com.oliferov.usdrateapp.domain

class GetUsdRateForTodayUseCase(private val repository: RepositoryUsdRate) {
    operator fun invoke() = repository.getUsdRateForToday()
}