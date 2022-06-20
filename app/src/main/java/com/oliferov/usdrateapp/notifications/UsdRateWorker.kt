package com.oliferov.usdrateapp.notifications

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.oliferov.usdrateapp.R
import com.oliferov.usdrateapp.data.data.UsdRateDao
import com.oliferov.usdrateapp.data.data.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.source.getCurrentTime
import com.oliferov.usdrateapp.source.isConnected
import com.oliferov.usdrateapp.source.loadData
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider
import kotlin.random.Random

class UsdRateWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
    private val apiService: UsdRateApiService,
    private val dao: UsdRateDao,
    private val mapperUsdRate: MapperUsdRate
) : CoroutineWorker(
    context,
    workerParameters
) {
    override suspend fun doWork(): Result {
        Log.e("DXDD","DOWORK RESTART")
//        if (isConnected(context)) {
//            val rub = inputData.getString(RUB_ID)?.toDouble()
//            loadData(apiService, dao, mapperUsdRate)
//            val usdRateForTodayDao = dao.getUsdRateForToday(getCurrentTime())
//            if(usdRateForTodayDao != null){
//                Log.e("DXD",usdRateForTodayDao.value)
//                val currentUsdRate =
//                    mapperUsdRate.converterValueStringToDouble(usdRateForTodayDao.value)
//                if (rub != null && rub <= currentUsdRate) {
//                    createNotification(rub.toString())
//                    return Result.success()
//                }
//            }
//        }
        return Result.retry()
    }

    class Factory @Inject constructor(
        private val apiService: Provider<UsdRateApiService>,
        private val dao: Provider<UsdRateDao>,
        private val mapperUsdRate: Provider<MapperUsdRate>
    ) : BaseWorkerFactory {
        override fun create(context: Context, params: WorkerParameters): UsdRateWorker {
            return UsdRateWorker(
                context,
                params,
                apiService.get(),
                dao.get(),
                mapperUsdRate.get()
            )
        }
    }

    private suspend fun createNotification(rub: String) {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(CONTENT_TITLE)
                    .setContentText(CONTENT_TEXT + rub)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .build()
            )
        )

    }

    companion object {
        private const val RUB_ID = "rubId"
        const val NAME_WORK = "UsdRateWorker"
        private const val CHANNEL_ID = "channel_id"
        private const val CONTENT_TITLE = "Курс доллара достиг отметки"
        private const val CONTENT_TEXT = "На сегодня доллар достиг отметки "

        fun makeRequest(rub: String): PeriodicWorkRequest {
            val data = Data.Builder().putString(RUB_ID, rub).build()
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PeriodicWorkRequestBuilder<UsdRateWorker>(Duration.ofSeconds(10))
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .setInputData(data)
                    .build()
            } else {
                PeriodicWorkRequestBuilder<UsdRateWorker>(10, TimeUnit.SECONDS)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .setInputData(data)
                    .build()
            }
        }
    }
}