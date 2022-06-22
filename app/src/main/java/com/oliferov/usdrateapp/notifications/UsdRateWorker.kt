package com.oliferov.usdrateapp.notifications

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.oliferov.usdrateapp.R
import com.oliferov.usdrateapp.data.database.UsdRateDao
import com.oliferov.usdrateapp.data.database.mapper.MapperUsdRate
import com.oliferov.usdrateapp.data.network.UsdRateApiService
import com.oliferov.usdrateapp.source.getCurrentTime
import com.oliferov.usdrateapp.source.isConnected
import com.oliferov.usdrateapp.source.loadData
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

    private val CONTENT_TITLE = context.getString(R.string.notification_title)
    private val CONTENT_TEXT = context.getString(R.string.notification_text)

    override suspend fun doWork(): Result {
        if (isConnected(context)) {
            val rub = inputData.getString(RUB_ID)?.toDouble()
            loadData(apiService, dao, mapperUsdRate)
            val usdRateForTodayDao = dao.getUsdRateForToday(
                getCurrentTime()
            )
            if (usdRateForTodayDao != null) {
                val currentUsdRate =
                    mapperUsdRate.converterValueStringToDouble(usdRateForTodayDao.value)
                if (rub != null && rub <= currentUsdRate) {
                    createNotification(rub.toString())
                    return Result.success()
                }
            }
        }
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

    private fun createNotification(rub: String) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(CONTENT_TITLE)
            .setContentText(CONTENT_TEXT + rub)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .build()
        val manager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        manager.notify(Random.nextInt(), notification)
    }

    companion object {
        private const val RUB_ID = "rubId"
        const val NAME_WORK = "UsdRateWorker"
        private const val CHANNEL_ID = "channel_id"


        fun makeRequest(rub: String): PeriodicWorkRequest {
            val data = Data.Builder().putString(RUB_ID, rub).build()
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PeriodicWorkRequestBuilder<UsdRateWorker>(1, TimeUnit.DAYS)
                    .setConstraints(
                        Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .build()
                    )
                    .setInputData(data)
                    .build()
            } else {
                PeriodicWorkRequestBuilder<UsdRateWorker>(1, TimeUnit.DAYS)
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