package com.oliferov.usdrateapp.data.repository

import com.oliferov.usdrateapp.data.data.UsdRateDao
import com.oliferov.usdrateapp.data.data.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.data.network.dto.ResponseDto
import com.oliferov.usdrateapp.data.network.dto.UsdRateDto
import com.oliferov.usdrateapp.domain.RepositoryUsdRate
import com.oliferov.usdrateapp.domain.UsdRate
import javax.inject.Inject


class RepositoryUsdRateImpl @Inject constructor(
    private val apiService: UsdRateApiService,
    private val usdRateDao: UsdRateDao,
    private val mapperUsdRate: MapperUsdRate
    ): RepositoryUsdRate {

    override suspend fun getUsdRateForTodayAndYesterday(yesterday: Int, today: Int) =
        mapperUsdRate
            .mapUsdRateDbModelListToUsdRateList(
                usdRateDao.getUsdRateForTodayAndYesterday(yesterday,today)
            )


    override suspend fun getUsdRatePerMonth(dateBeginningMonth: String, dateEndMonth: String): List<UsdRate> {
        val listDto = loadData(dateBeginningMonth,dateEndMonth)
        if(listDto.isNotEmpty()){
            usdRateDao.
            insertAllUsdRate(
                mapperUsdRate
                    .mapUsdRateDtoListToUsdRateDbModelList(listDto)
            )
        }
       return mapperUsdRate
            .mapUsdRateDbModelListToUsdRateList(
                usdRateDao.getAllUsdRate()
        )
    }

    private suspend fun loadData(dateBeginningMonth: String, dateEndMonth: String)=
        apiService
            .getUsdRatePerMonth(dateBeginningMonth,dateEndMonth)
            .body()
            ?.arrayDollar
            ?: emptyList<UsdRateDto>()
}