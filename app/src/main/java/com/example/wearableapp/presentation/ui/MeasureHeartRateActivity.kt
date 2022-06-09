package com.example.wearableapp.presentation.ui

import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.domain.model.HeartRateData
import com.example.wearableapp.R
import com.example.wearableapp.databinding.ActivityMeasHeartRateBinding
import com.example.wearableapp.presentation.adapter.HeartDataAdapter
import com.example.wearableapp.presentation.utils.Constants.Companion.DEFAULT_NAME
import com.example.wearableapp.presentation.viewmodel.MeasureHeartRateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.util.jar.Manifest
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
        binding.heratRateCount.text = "---"
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
        }
        //remove this line once actual data retrieved
        measureHRList.add(HeartRateData(0.0, Date().time,""))
        setHeartGraphData()
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
           R.id.tvHeartRateStartId->{checkPermission(android.Manifest.permission.BODY_SENSORS,100)}
           R.id.tvHeartRateStopId->{stopMeasureHRView()}
       }
    }


    override fun onDestroy() {
        super.onDestroy()
        measureHRList.clear()
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            startMeasureHRView()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startMeasureHRView()
            } else {
                binding.tvHeartStartId.text = resources.getString(R.string.sensor_permission_required)
                binding.tvHeartStartId.setCompoundDrawables(null,null,null,null)
                binding.tvHeartStartId.setTextColor(resources.getColor(R.color.digital_text))
            }
        }
    }





}