package com.oliferov.usdrateapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class,ViewModelModule::class])
interface AppComponent {

    fun inject(application: UsdRateApplication)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance application:Application): AppComponent
    }
}