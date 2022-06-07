package com.oliferov.usdrateapp.di

import androidx.lifecycle.ViewModel
import com.oliferov.usdrateapp.presentation.listusd.UsdRateListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsdRateListViewModel::class)
    fun bindUserListViewModel(viewModel: UsdRateListViewModel): ViewModel
}