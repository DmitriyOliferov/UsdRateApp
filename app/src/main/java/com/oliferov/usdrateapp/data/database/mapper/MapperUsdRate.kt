package com.oliferov.usdrateapp.data.database.mapper

import com.oliferov.usdrateapp.data.database.model.UsdRateDbModel
import com.oliferov.usdrateapp.data.network.dto.UsdRateDto
import com.oliferov.usdrateapp.domain.UsdRate
import javax.inject.Inject

class MapperUsdRate @Inject constructor() {
    fun mapUsdRateDtoListToUsdRateDbModelList(listDto: List<UsdRateDto>): List<UsdRateDbModel> {
        return listDto.map {
            mapUsdRateDtoToUsdRateDbModel(it)
        }
    }

    private fun mapUsdRateDtoToUsdRateDbModel(dto: UsdRateDto) = UsdRateDbModel(
        date = dto.date ?: "-1",
        nominal = dto.nominal ?: -1,
        value = dto.value ?: "-1"
    )

    fun mapUsdRateDbModelListToUsdRateList(listDbModel: List<UsdRateDbModel>): List<UsdRate> {
        return listDbModel.map {
            mapUsdRateDbModelToUsdRate(it)
        }
    }

    private fun mapUsdRateDbModelToUsdRate(dbModel: UsdRateDbModel) =
        UsdRate(
            date = dbModel.date,
            nominal = dbModel.nominal,
            value = converterValueStringToDouble(dbModel.value)
        )

    fun converterValueStringToDouble(value: String) = value
        .split(",")
        .let {
            "${it[0]}.${it[1]}"
        }
        .toDouble()
}