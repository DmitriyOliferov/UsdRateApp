package com.oliferov.usdrateapp.domain

class GetUsdRateForTodayUseCase(private val repository: RepositoryUsdRate) {
    suspend operator fun invoke() = repository.getUsdRateForToday()
}