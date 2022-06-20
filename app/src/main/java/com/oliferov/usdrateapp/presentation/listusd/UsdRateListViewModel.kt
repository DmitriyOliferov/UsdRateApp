package com.oliferov.usdrateapp.presentation.listusd

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliferov.usdrateapp.data.data.UsdRateDao
import com.oliferov.usdrateapp.data.data.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.domain.AddNotificationUsdRateUseCase
import com.oliferov.usdrateapp.domain.GetUsdRatePerMonthUseCase
import com.oliferov.usdrateapp.domain.UsdRate
import com.oliferov.usdrateapp.source.loadData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsdRateListViewModel @Inject constructor(
    val addNotificationUsdRateUseCase: AddNotificationUsdRateUseCase,
    val getUsdRatePerMonthUseCase: GetUsdRatePerMonthUseCase,
    val apiService: UsdRateApiService,
    val dao: UsdRateDao,
    val mapperUsdRate: MapperUsdRate
): ViewModel() {
    private val _usdRateList = MutableLiveData<List<UsdRate>>()
    val usdRateList: LiveData<List<UsdRate>>
    get() = _usdRateList

    private suspend fun getUsdRateList() = getUsdRatePerMonthUseCase()

    private fun addNotificationUsdRate() = addNotificationUsdRateUseCase("40")

    val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        Log.e("DXD", throwable.message.toString())
    }

    init {
        viewModelScope.launch(Dispatchers.IO){
            Log.d("DXD",usdRateList.value.toString())
            _usdRateList.postValue(getUsdRateList() ?: emptyList())
            Log.d("DXD",usdRateList.value.toString())
//            withContext(Dispatchers.Main){

//            }
        }
        addNotificationUsdRate()
    }
}