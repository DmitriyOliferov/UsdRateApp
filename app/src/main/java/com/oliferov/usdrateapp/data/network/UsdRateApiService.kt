package com.oliferov.usdrateapp.data.network

import com.oliferov.usdrateapp.data.network.dto.ResponseDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface UsdRateApiService {

    @GET("XML_dynamic.asp")
    suspend fun getUsdRatePerMonth(
        @Query(TITLE_DATE_REQ1) date1:String,
        @Query(TITLE_DATE_REQ2) date2:String,
        @Query(TITLE_VAL_NM_RQ) valNmRq:String =  "R01235"
    ): Flow<ResponseDto>

    companion object{
        private const val TITLE_DATE_REQ1 = "date_req1"
        private const val TITLE_DATE_REQ2 = "date_req2"
        private const val TITLE_VAL_NM_RQ = "VAL_NM_RQ"
    }
}