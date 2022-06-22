package com.oliferov.usdrateapp.source

import com.oliferov.usdrateapp.data.database.UsdRateDao
import com.oliferov.usdrateapp.data.database.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.data.network.dto.UsdRateDto

suspend fun loadData(
    apiService: UsdRateApiService,
    usdRateDao: UsdRateDao,
    mapperUsdRate: MapperUsdRate
) {

    val currentDate = getCurrentAndPastMonth()
    val listDto = apiService
        .getUsdRatePerMonth(
            date1 = currentDate.first,
            date2 = currentDate.second

        )
        .body()
        ?.arrayDollar
        ?: emptyList<UsdRateDto>()
    if (listDto.isNotEmpty()) {
        usdRateDao.deleteAllUsdRate()
        usdRateDao.insertAllUsdRate(
            mapperUsdRate
                .mapUsdRateDtoListToUsdRateDbModelList(listDto)
        )
    }
}