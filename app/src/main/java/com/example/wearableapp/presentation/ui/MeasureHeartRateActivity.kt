package com.example.wearableapp.presentation.ui

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.domain.model.HeartRateData
import com.example.wearableapp.R
import com.example.wearableapp.databinding.ActivityMeasHeartRateBinding
import com.example.wearableapp.presentation.adapter.HeartDataAdapter
import com.example.wearableapp.presentation.service.HeartRateSensorService
import com.example.wearableapp.presentation.utils.CheckPermission
import com.example.wearableapp.presentation.viewmodel.MeasureHeartRateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MeasureHeartRateActivity : AppCompatActivity(){

    val TAG = MeasureHeartRateActivity::class.simpleName
    private lateinit var binding: ActivityMeasHeartRateBinding

    private val viewModel by viewModel<MeasureHeartRateViewModel>()

    private var measureHRList = ArrayList<HeartRateData>()

    var animator = AnimationDrawable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMeasHeartRateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        viewModel.getSavedData().observe(this, Observer {
            if(it.isNotEmpty()) {
                Log.d(TAG, "Saved Value:" + it.last().heartRateBpm)
                measureHRList.addAll(it)
                if (binding.clHeartStopId.visibility == View.VISIBLE) {
                    binding.heratRateCount.text = it.last().heartRateBpm.toString()
                }
                setHeartGraphData()
            }
        })
    }


    private fun setListeners() {
        binding.tvHeartRateStartId.setOnClickListener{
            if (CheckPermission.isPermissionCheck(this)) {
                startMeasureHRView()
            }else{
                Toast.makeText(this,"Sensor permission required",Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvHeartRateStopId.setOnClickListener{
            stopMeasureHRView()
        }
    }

    private fun startAnimation(){
        binding.floatLikeId.setBackgroundResource(R.drawable.animation_list)
        animator = binding.floatLikeId.background as AnimationDrawable
         if(measureHRList.isNotEmpty()){
             binding.heratRateCount.text = measureHRList.last().heartRateBpm.toString()}else{
             binding.heratRateCount.text = "0.0"
         }
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
    }

    private fun stopMeasureHRView(){
        stopAnimation()
        binding.clHeartStartId.visibility = View.VISIBLE
        binding.clHeartStopId.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        checkSensorIsAvailable()
    }


    private fun setHeartGraphData(){
        binding.heartRateGraphId.let {
            it.lineColor = resources.getColor(R.color.color_berry)
            it.setBackgroundColor(resources.getColor(R.color.color_black))
            it.lineWidth = 2f
        }
        binding.heartRateGraphId.adapter = HeartDataAdapter(viewModel.setDummyHeartData(measureHRList))
    }

    private fun checkSensorIsAvailable(){
        startForegroundService(Intent(this, HeartRateSensorService::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        false
    }







}