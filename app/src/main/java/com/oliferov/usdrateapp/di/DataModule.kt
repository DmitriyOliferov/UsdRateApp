package com.oliferov.usdrateapp.di

import com.oliferov.usdrateapp.data.data.AppDatabase
import com.oliferov.usdrateapp.data.repository.RepositoryUsdRateImpl
import com.oliferov.usdrateapp.domain.RepositoryUsdRate
import dagger.Binds
import dagger.Module

@Module(includes = [
    ApiModule::class,
    MapperModule::class,
    AppDatabaseModule::class
])
interface DataModule{

    @Binds
    fun bindRepository(repositoryUsdRateImpl: RepositoryUsdRateImpl):RepositoryUsdRate
}