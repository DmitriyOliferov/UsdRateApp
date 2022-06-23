package com.oliferov.usdrateapp.di

import android.app.Application
import androidx.work.Configuration
import com.oliferov.usdrateapp.notifications.UsdRateWorkerFactory
import javax.inject.Inject


class UsdRateApplication : Application(), Configuration.Provider {

    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    @Inject
    lateinit var workerFactory: UsdRateWorkerFactory

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder().setWorkerFactory(workerFactory).build()
}
