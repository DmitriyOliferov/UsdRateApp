package com.oliferov.usdrateapp.source

import android.util.Log
import com.oliferov.usdrateapp.data.data.UsdRateDao
import com.oliferov.usdrateapp.data.data.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.data.network.dto.UsdRateDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun loadData(
    apiService: UsdRateApiService,
    usdRateDao: UsdRateDao,
    mapperUsdRate: MapperUsdRate
) {
    Log.e("DXD", "start Load")
    val currentDate = getCurrentAndPastMonth()
    Log.e("DXD", "first Load")
    Log.e("DXD", currentDate.first +" " + currentDate.second)
    val listDto = apiService
            .getUsdRatePerMonth(date1 = currentDate.first, date2 = currentDate.second)
            .body()
            ?.arrayDollar
            ?:
            emptyList<UsdRateDto>()

    Log.e("DXD", "second Load")
    if (listDto.isNotEmpty()) {
        usdRateDao.deleteAllUsdRate()
        usdRateDao.insertAllUsdRate(
            mapperUsdRate
                .mapUsdRateDtoListToUsdRateDbModelList(listDto)
        )
    }
    Log.e("DXD", "out Load" + listDto.toString())
}