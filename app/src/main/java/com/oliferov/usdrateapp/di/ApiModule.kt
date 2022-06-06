package com.oliferov.usdrateapp.di

import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(tikXml: TikXmlConverterFactory): Retrofit {
        val BASE_URL = "http://cbr.ru/scripts/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(tikXml)
            .build()
    }

    @Provides
    @Singleton
    fun provideTikXml(): TikXmlConverterFactory{
        return TikXmlConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): UsdRateApiService{
        return retrofit.create(UsdRateApiService::class.java)
    }

}