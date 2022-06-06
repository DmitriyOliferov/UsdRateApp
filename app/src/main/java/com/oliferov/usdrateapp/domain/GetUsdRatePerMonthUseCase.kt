package com.oliferov.usdrateapp.domain

class GetUsdRatePerMonthUseCase (private val repository: RepositoryUsdRate) {
    operator fun invoke() = repository.getUsdRatePerMonth()
}