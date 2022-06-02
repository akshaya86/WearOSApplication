package com.example.wearableapp.presentation.ui

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.wearableapp.databinding.ActivityMeasHeartRateBinding


class MeasHeartRateActivity : Activity(), SensorEventListener {

    private lateinit var binding: ActivityMeasHeartRateBinding

    private var mSensorManager: SensorManager? = null
    private var mHeartSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeasHeartRateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHeartExConfirmId.setOnClickListener {
            binding.clExportHeartConfirmId.visibility = View.GONE
            binding.clExportHeartRateMsgId.visibility = View.VISIBLE
            binding.tvHeartExportUploadingId.visibility = View.VISIBLE
        }

        binding.tvHeartExportUploadingId.setOnClickListener {
            binding.tvHeartExportedDataId.visibility = View.VISIBLE
            binding.tvHeartExportUploadingId.visibility = View.GONE
        }

        getSensorData()
    }

    private fun getSensorData() {
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mHeartSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_HEART_RATE)
    }

    override fun onResume() {
        super.onResume()
        mSensorManager?.registerListener(
            this,
            mHeartSensor,
            SensorManager.SENSOR_DELAY_FASTEST
        );
    }

    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val heartRate = event.values[0]
            binding.heratRateCount.text = heartRate.toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("Sensor","Sensor Data-"+sensor?.name+"\t\n"+accuracy)
    }
}