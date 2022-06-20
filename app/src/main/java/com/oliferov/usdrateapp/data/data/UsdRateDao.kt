package com.oliferov.usdrateapp.data.data

import androidx.room.*
import com.oliferov.usdrateapp.data.data.model.UsdRateDbModel

@Dao
interface UsdRateDao {
    @Query("SELECT * FROM usd_rate ORDER BY date")
    fun getAllUsdRate(): List<UsdRateDbModel>

    @Query("SELECT * FROM usd_rate WHERE date == :today ORDER BY date LIMIT 1")
    fun getUsdRateForToday(today: String): UsdRateDbModel

    @Insert(entity = UsdRateDbModel::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsdRate(usdRate: List<UsdRateDbModel>)

    @Query("DELETE FROM usd_rate")
    fun deleteAllUsdRate()
}