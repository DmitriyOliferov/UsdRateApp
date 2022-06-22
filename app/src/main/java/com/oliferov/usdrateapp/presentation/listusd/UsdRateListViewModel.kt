package com.oliferov.usdrateapp.presentation.listusd

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarEntry
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.oliferov.usdrateapp.data.data.UsdRateDao
import com.oliferov.usdrateapp.data.data.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.domain.AddNotificationUsdRateUseCase
import com.oliferov.usdrateapp.domain.GetUsdRatePerMonthUseCase
import com.oliferov.usdrateapp.domain.UsdRate
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class UsdRateListViewModel @Inject constructor(
    private val addNotificationUsdRateUseCase: AddNotificationUsdRateUseCase,
    private val getUsdRatePerMonthUseCase: GetUsdRatePerMonthUseCase,
    private val apiService: UsdRateApiService,
    private val dao: UsdRateDao,
    private val mapperUsdRate: MapperUsdRate,
    private val application: Application
): ViewModel() {
    private val _usdRateList = MutableLiveData<List<UsdRate>>()
    val usdRateList: LiveData<List<UsdRate>>
    get() = _usdRateList

    private suspend fun getUsdRateList() = getUsdRatePerMonthUseCase()

    fun addNotificationUsdRate(rub: String) = addNotificationUsdRateUseCase(rub)


    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        Log.e("DXD", throwable.message.toString())
    }

    fun loadData(){
        viewModelScope.launch(Dispatchers.IO){
            _usdRateList.postValue(getUsdRateList() ?: emptyList())
        }
    }

    init {
        loadData()
    }

    fun getListForGraph(): List<BarEntry>{
        var id = 1f;
        return usdRateList.value?.map {
            BarEntry(id++,it.value.toFloat())
        } ?: emptyList<BarEntry>()
    }
}