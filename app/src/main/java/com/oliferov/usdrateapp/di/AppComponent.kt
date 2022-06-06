package com.oliferov.usdrateapp.di

import dagger.Component

@Component(modules = [DataModule::class,ViewModelModule::class])
interface AppComponent {

//    fun getRepository(): RepositoryUsdRateImpl

//    fun inject()
}