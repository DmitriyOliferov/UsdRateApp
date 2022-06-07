package com.oliferov.usdrateapp.data.network.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "Record")
data class UsdRateDto(
    @Attribute(name = "Date")
    val date: String? = "",
    @Attribute(name = "Id")
    val id: String? = "",
    @PropertyElement(name = "Value")
    val value: String? = "",
    @PropertyElement(name = "Nominal")
    val nominal: Int? = 0
)
