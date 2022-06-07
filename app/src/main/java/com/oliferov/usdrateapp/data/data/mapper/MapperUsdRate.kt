package com.oliferov.usdrateapp.data.data.mapper

import com.oliferov.usdrateapp.data.data.model.UsdRateDbModel
import com.oliferov.usdrateapp.data.network.dto.UsdRateDto
import com.oliferov.usdrateapp.domain.UsdRate
import java.text.SimpleDateFormat
import java.util.*

object MapperUsdRate {
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
            date = converterDateStringToCalendarDate(dbModel.date),
            nominal = dbModel.nominal,
            value = converterValueStringToInt(dbModel.value)
        )

    private fun converterValueStringToInt(value: String) = value
        .split(",")
        .let {
            "$it[0].$it[1]"
        }
        .toDouble()

    private fun converterDateStringToCalendarDate(date: String) =
        SimpleDateFormat(
            "dd.MM.yyyy",
            Locale.ROOT
        ).parse(date) ?: Date()

}