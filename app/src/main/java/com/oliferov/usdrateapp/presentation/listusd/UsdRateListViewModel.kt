package com.oliferov.usdrateapp.presentation.listusd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliferov.usdrateapp.domain.GetUsdRatePerMonthUseCase
import com.oliferov.usdrateapp.domain.UsdRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsdRateListViewModel @Inject constructor(
    val getUsdRatePerMonthUseCase: GetUsdRatePerMonthUseCase
): ViewModel() {
    private val _usdRateList = MutableLiveData<List<UsdRate>>()
    val usdRateList: LiveData<List<UsdRate>>
    get() = _usdRateList

    private suspend fun getUsdRateList() = getUsdRatePerMonthUseCase.invoke("07/05/2022","07/06/2022")

    init {
        viewModelScope.launch(Dispatchers.IO){
            _usdRateList.postValue(getUsdRateList() ?: emptyList())
        }
    }
}