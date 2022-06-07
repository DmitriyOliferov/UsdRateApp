package com.oliferov.usdrateapp.di

import com.oliferov.usdrateapp.data.data.mapper.MapperUsdRate
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule {

    @Provides
    @Singleton
    fun providesMapper(): MapperUsdRate{
        return MapperUsdRate()
    }
}