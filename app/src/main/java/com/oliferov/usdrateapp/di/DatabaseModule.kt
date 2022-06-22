package com.oliferov.usdrateapp.di

import android.app.Application
import androidx.room.Room
import com.oliferov.usdrateapp.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    private const val DB_NAME = "UsdRate.db"

    @Provides
    @Singleton
    fun providesDatabase(
        application: Application
    )  = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        DB_NAME
    ).build()

    @Provides
    @Singleton
    fun providesDao(db: AppDatabase) = db.getUsdRateDao()
}
