package com.oliferov.usdrateapp.di

import dagger.Module

@Module(includes = [ApiModule::class, AppDatabaseModule::class])
interface DataModule