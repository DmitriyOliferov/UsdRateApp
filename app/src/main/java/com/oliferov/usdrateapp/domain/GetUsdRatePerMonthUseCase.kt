package com.oliferov.usdrateapp.domain

class GetUsdRatePerMonthUseCase (private val repository: RepositoryUsdRate) {
    suspend operator fun invoke(dateBeginningMonth: String, dateEndMonth: String) = repository.getUsdRatePerMonth(dateBeginningMonth, dateEndMonth)
}