package com.oliferov.usdrateapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oliferov.usdrateapp.data.database.model.UsdRateDbModel

@Database(entities = [UsdRateDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUsdRateDao(): UsdRateDao
}