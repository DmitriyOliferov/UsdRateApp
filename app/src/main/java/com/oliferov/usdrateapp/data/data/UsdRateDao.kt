package com.oliferov.usdrateapp.data.data

import androidx.room.*
import com.oliferov.usdrateapp.data.data.model.UsdRateDbModel

@Dao
interface UsdRateDao {
    @Query("SELECT * FROM usd_rate ORDER BY date")
    fun getAllUsdRate(): List<UsdRateDbModel>

    @Query("SELECT * FROM usd_rate WHERE date == :yesterday OR date == :today ORDER BY date LIMIT 2")
    fun getUsdRateForTodayAndYesterday(yesterday: Int, today: Int): List<UsdRateDbModel>

    @Insert(entity = UsdRateDbModel::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsdRate(usdRate: List<UsdRateDbModel>)

    @Query("DELETE FROM usd_rate")
    fun deleteAllUsdRate()
}