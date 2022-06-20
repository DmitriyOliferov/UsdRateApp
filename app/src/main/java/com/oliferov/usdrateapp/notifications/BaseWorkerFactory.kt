package com.oliferov.usdrateapp.notifications

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters

interface BaseWorkerFactory{
    fun create(context: Context, params: WorkerParameters): ListenableWorker
}