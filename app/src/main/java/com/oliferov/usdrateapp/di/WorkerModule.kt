package com.oliferov.usdrateapp.di

import com.oliferov.usdrateapp.notifications.BaseWorkerFactory
import com.oliferov.usdrateapp.notifications.UsdRateWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(UsdRateWorker::class)
    fun bindUsdRateWorker(factory: UsdRateWorker.Factory): BaseWorkerFactory

}