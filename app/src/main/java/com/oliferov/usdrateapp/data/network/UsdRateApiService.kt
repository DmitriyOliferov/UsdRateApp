package com.oliferov.usdrateapp.data.network

import com.oliferov.usdrateapp.data.network.dto.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsdRateApiService {

    @GET("XML_dynamic.asp")
    suspend fun getUsdRatePerMonth(
        @Query(TITLE_DATE_REQ1) date1: String = DEFAULT_DATE_REQ1,
        @Query(TITLE_DATE_REQ2) date2: String = DEFAULT_DATE_REQ2,
        @Query(TITLE_VAL_NM_RQ) valNmRq: String = KEY_VAL_NM_RQ
    ): Response<ResponseDto>

    companion object {
        private const val TITLE_DATE_REQ1 = "date_req1"
        private const val DEFAULT_DATE_REQ1 = "12/04/2021"
        private const val DEFAULT_DATE_REQ2 = "12/05/2021"
        private const val TITLE_DATE_REQ2 = "date_req2"
        private const val TITLE_VAL_NM_RQ = "VAL_NM_RQ"
        private const val KEY_VAL_NM_RQ = "R01235"

    }
}