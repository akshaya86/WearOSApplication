package com.example.wearableapp.presentation.ui

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.domain.model.HeartRateData
import com.example.wearableapp.R
import com.example.wearableapp.databinding.ActivityMeasHeartRateBinding
import com.example.wearableapp.presentation.adapter.HeartDataAdapter
import com.example.wearableapp.presentation.service.HeartRateSensorService
import com.example.wearableapp.presentation.utils.Constants.Companion.DEFAULT_NAME
import com.example.wearableapp.presentation.viewmodel.MeasureHeartRateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList


class MeasureHeartRateActivity : AppCompatActivity(), SensorEventListener, View.OnClickListener {

    private lateinit var binding: ActivityMeasHeartRateBinding

    private val viewModel by viewModel<MeasureHeartRateViewModel>()

    private var mSensorManager: SensorManager? = null
    private var mHeartSensor: Sensor? = null

    private var measureHRList = ArrayList<HeartRateData>()

    var animator = AnimationDrawable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeasHeartRateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        checkSensorIsAvailable()
    }

    private fun setListeners() {
        binding.tvHeartRateStartId.setOnClickListener(this)
        binding.tvHeartRateStopId.setOnClickListener(this)
    }

    private fun startAnimation(){
        binding.floatLikeId.setBackgroundResource(R.drawable.animation_list)
        animator = binding.floatLikeId.background as AnimationDrawable
        animator.start()
    }

    private fun stopAnimation(){
        animator.stop()
        binding.floatLikeId.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        binding.heratRateCount.text = "--"
    }

    private fun startMeasureHRView(){
        binding.clHeartStartId.visibility = View.GONE
        binding.clHeartStopId.visibility = View.VISIBLE
        startAnimation()
        mSensorManager?.registerListener(
            this,
            mHeartSensor,
            SensorManager.SENSOR_DELAY_FASTEST
        );
    }

    private fun stopMeasureHRView(){
        stopAnimation()
        binding.clHeartStartId.visibility = View.VISIBLE
        binding.clHeartStopId.visibility = View.GONE
        mSensorManager?.unregisterListener(this)
    }

    private fun getSensorData() {
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mHeartSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_HEART_RATE)
    }

    override fun onResume() {
        super.onResume()
        getSensorData()
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val heartRate = event.values[0]
            binding.heratRateCount.text = heartRate.toInt().toString()
            measureHRList.add(HeartRateData(event.values[0].toDouble(),event.timestamp,DEFAULT_NAME))
            setHeartGraphData()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun setHeartGraphData(){
        binding.heartRateGraphId.let {
            it.lineColor = resources.getColor(R.color.color_berry)
            it.setBackgroundColor(resources.getColor(R.color.color_black))
            it.lineWidth = 2f
        }
        binding.heartRateGraphId.adapter = HeartDataAdapter(viewModel.setDummyHeartData(measureHRList))
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.tvHeartRateStartId->{startMeasureHRView();
                                     addHeartRateData()}
           R.id.tvHeartRateStopId->{stopMeasureHRView()}
       }
    }

    private fun addHeartRateData() {
        Log.d("data-insert", "" + measureHRList.size.toString())
        lifecycleScope.launch(Dispatchers.IO){
            viewModel.insertData(measureHRList)
        }
    }

    private fun checkSensorIsAvailable(){
        startForegroundService(Intent(this, HeartRateSensorService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        measureHRList.clear()
    }





}