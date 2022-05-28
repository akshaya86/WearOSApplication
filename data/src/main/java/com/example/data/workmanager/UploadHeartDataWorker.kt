package com.example.data.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadHeartDataWorker(val context: Context, val workerParams: WorkerParameters) :
Worker(context, workerParams)  {
    override fun doWork(): Result {
        //uploading stuff to db
        return Result.success()
    }
}