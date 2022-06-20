package com.oliferov.usdrateapp.domain

import javax.inject.Inject

class GetUsdRatePerMonthUseCase @Inject constructor(private val repository: RepositoryUsdRate) {
    suspend operator fun invoke() = repository.getUsdRatePerMonth()
}