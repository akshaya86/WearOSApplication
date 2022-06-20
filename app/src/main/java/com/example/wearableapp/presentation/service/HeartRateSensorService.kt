package com.example.wearableapp.presentation.service

import android.app.*
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.domain.model.HeartRateData
import com.example.domain.repository.IHeartRateHistoryRepository
import com.example.domain.usecase.GetHeartRateOperationsUseCase
import com.example.wearableapp.R
import com.example.wearableapp.presentation.ui.MeasureHeartRateActivity
import com.example.wearableapp.presentation.utils.Constants.Companion.DEFAULT_NAME
import com.example.wearableapp.presentation.utils.Constants.Companion.SENSOR_ID
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import java.util.*


class HeartRateSensorService : Service(), SensorEventListener, KoinComponent {

    private val repository : IHeartRateHistoryRepository by inject()
    private lateinit var mSensorManager: SensorManager
    private lateinit var mHeartSensor: Sensor


    companion object {
        var isServiceRunning = false
    }

    override fun onCreate() {
        super.onCreate()
    }

    fun notifyApp(){
        val intent = Intent(this, MeasureHeartRateActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val name = getString(R.string.sensor_notification)
            val channel =
                NotificationChannel(SENSOR_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, SENSOR_ID)
            .setContentTitle(this.getString(R.string.sensor_title))
            .setSmallIcon(R.drawable.ic_heart_full_icon)
            .setContentText(getString(R.string.sensor_desc))
            .setOngoing(true)
            .setContentIntent(pendingIntent)
        startForeground(1, notification.build())

       /* lifecycleScope.launchWhenCreated {
            getHeartRateOperationsUseCase.deleteHeartRateData()
        }*/
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        isServiceRunning = true
        notifyApp()
        registerService()
        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.apply {
            if (sensor.type == Sensor.TYPE_HEART_RATE && values.isNotEmpty()) {
                val heartRate = values[0]
                Log.e("Senesor called from:Service", "$heartRate")
                insertHeartRateData(heartRate.toInt())
            }
        }
    }

    private fun insertHeartRateData(heartRate: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertHeartRateData(HeartRateData(heartRate.toDouble(),Date().time,
                DEFAULT_NAME))
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
    }

    override fun onBind(intent: Intent?): IBinder? {
       return null
    }

    private fun registerService(){
        mSensorManager = (getSystemService(SENSOR_SERVICE) as SensorManager)
        mHeartSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        mSensorManager.registerListener(this, mHeartSensor, SensorManager.SENSOR_DELAY_FASTEST)

    }
}