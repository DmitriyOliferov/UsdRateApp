package com.oliferov.usdrateapp.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.oliferov.usdrateapp.notifications.UsdRateWorkerFactory
import javax.inject.Inject


class UsdRateApplication : Application() ,Configuration.Provider
{

    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)

    }

    @Inject
    lateinit var workerFactory: UsdRateWorkerFactory

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
//        WorkManager.initialize(this, workManagerConfiguration)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder().setWorkerFactory(workerFactory).build()
}
