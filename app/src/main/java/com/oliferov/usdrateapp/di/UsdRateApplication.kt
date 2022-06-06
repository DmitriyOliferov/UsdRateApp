package com.oliferov.usdrateapp.di

import android.app.Application

class UsdRateApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}