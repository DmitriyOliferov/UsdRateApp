package com.oliferov.usdrateapp.domain

class GetUsdRateForTodayAndYesterdayUseCase(private val repository: RepositoryUsdRate) {
    suspend operator fun invoke(yesterday: Int, today: Int) = repository.getUsdRateForTodayAndYesterday(yesterday, today)
}