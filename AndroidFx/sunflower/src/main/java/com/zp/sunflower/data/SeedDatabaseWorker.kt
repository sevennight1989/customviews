package com.zp.sunflower.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SeedDatabaseWorker(context: Context,workerParameters: WorkerParameters):CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {

    }
}