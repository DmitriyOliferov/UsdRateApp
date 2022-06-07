package com.oliferov.usdrateapp.di

import android.app.Application
import androidx.room.Room
import com.oliferov.usdrateapp.data.data.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    )  = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "UsdRate.db"
    ).build()

    @Provides
    @Singleton
    fun provideDao(db: AppDatabase) = db.getUsdRateDao()
}