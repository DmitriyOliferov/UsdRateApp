package com.oliferov.usdrateapp.di

import android.app.Application
import com.oliferov.usdrateapp.presentation.listusd.CustomDialog
import com.oliferov.usdrateapp.presentation.listusd.UsdRateListFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [DataModule::class,
        ViewModelModule::class,
        WorkerModule::class,
        AppModule::class
    ]
)
@Singleton
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

    fun inject(application: UsdRateApplication)

    fun inject(fragment: UsdRateListFragment)

    fun inject(fragment: CustomDialog)
}