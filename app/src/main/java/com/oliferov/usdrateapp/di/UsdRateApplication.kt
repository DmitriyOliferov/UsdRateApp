package com.oliferov.usdrateapp.di

import android.app.Application


class UsdRateApplication: Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(this)
    }
}