package com.oliferov.usdrateapp.di

import com.oliferov.usdrateapp.data.data.mapper.MapperUsdRate
import dagger.Binds
import dagger.Module

@Module
interface MapperModule {
    @Binds
    fun bindMapper(mapper: MapperUsdRate): MapperUsdRate
}