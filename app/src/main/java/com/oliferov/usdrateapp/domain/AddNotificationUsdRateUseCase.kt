package com.oliferov.usdrateapp.domain

import javax.inject.Inject

class AddNotificationUsdRateUseCase @Inject constructor(private val repository: RepositoryUsdRate) {
    operator fun invoke(rub: String) = repository.addNotificationUsdRate(rub)
}