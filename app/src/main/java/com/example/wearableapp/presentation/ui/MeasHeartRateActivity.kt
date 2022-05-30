package com.example.wearableapp.presentation.ui

import android.app.Activity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.wearableapp.R
import com.example.wearableapp.databinding.ActivityMeasHeartRateBinding
import com.example.wearableapp.presentation.utils.Constants.Companion.DEFAULT_NAME
import com.example.wearableapp.presentation.utils.Constants.Companion.FLAG_EXPORT_HR
import com.example.wearableapp.presentation.utils.Constants.Companion.FLAG_HR
import com.example.wearableapp.presentation.utils.Constants.Companion.HR_TYPE
import com.example.wearableapp.presentation.viewmodel.MainViewModel
import com.example.wearableapp.presentation.viewmodel.MeasureHeartRateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MeasHeartRateActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityMeasHeartRateBinding

    private val viewModel by viewModel<MeasureHeartRateViewModel>()

    private var mSensorManager: SensorManager? = null
    private var mHeartSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeasHeartRateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSensorData()
        getHrType()
        setListeners()
        setHeartGraphData()

    }

    private fun getHrType() {
        val type = intent.getStringExtra(HR_TYPE)
        when(type){
            FLAG_HR->{
                binding.clHeartStartId.visibility = View.VISIBLE
                binding.clExportHeartConfirmId.visibility = View.GONE
            }
            FLAG_EXPORT_HR->{
                binding.clHeartStartId.visibility = View.GONE
                binding.clExportHeartConfirmId.visibility = View.VISIBLE
                binding.flHeartRateIndicatorId.visibility = View.GONE
            }
        }
    }

    private fun setListeners() {
        binding.tvHeartExConfirmId.setOnClickListener {
            binding.clExportHeartConfirmId.visibility = View.GONE
            binding.clExportHeartRateMsgId.visibility = View.VISIBLE
            binding.tvHeartExportUploadingId.visibility = View.VISIBLE
        }

        binding.tvHeartExportUploadingId.setOnClickListener {
            binding.tvHeartExportedDataId.visibility = View.VISIBLE
            binding.tvHeartExportUploadingId.visibility = View.GONE
        }

        binding.tvHeartRateStartId.setOnClickListener {
            binding.clHeartStartId.visibility = View.GONE
            binding.clHeartStopId.visibility = View.VISIBLE
            startAnimation()
        }

        binding.tvHeartRateStopId.setOnClickListener {
            stopAnimation()
            binding.clHeartStartId.visibility = View.VISIBLE
            binding.clHeartStopId.visibility = View.GONE
        }
    }

    private fun startAnimation(){
        binding.floatLikeId.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        binding.floatLikeId.startAnimation(AnimationUtils.loadAnimation(this,R.anim.beatanim))
        binding.heratRateCount.text = "50"
    }

    private fun stopAnimation(){
        binding.floatLikeId.setBackgroundResource(R.drawable.ic_heart_inline_icon)
        binding.floatLikeId.clearAnimation()
        binding.heratRateCount.text = "--"
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
            viewModel.heartLiveData.value?.ratebpm = heartRate.toDouble()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("Sensor","Sensor Data-"+sensor?.name+"\t\n"+accuracy)
    }

    private fun getHeartMeasure(){
        viewModel.sendHeartRateData(DEFAULT_NAME)
    }

    private fun setHeartGraphData(){
        binding.heartRateGraphId.let {
            it.lineColor = resources.getColor(R.color.color_berry)
            it.setBackgroundColor(resources.getColor(R.color.color_black))
            it.lineWidth = 2f
        }
        binding.heartRateGraphId.adapter = HeartDataAdapter(viewModel.setDummyHeartData())
    }
}