package com.example.wearableapp.presentation.ui
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wearableapp.R
import com.example.wearableapp.databinding.ActivityMainBinding
import com.example.wearableapp.presentation.utils.Constants.Companion.FLAG_EXPORT_HR
import com.example.wearableapp.presentation.utils.Constants.Companion.FLAG_HR
import com.example.wearableapp.presentation.utils.Constants.Companion.HR_TYPE


class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkSensorIsAvailable()
        binding.rvListId.hasFixedSize()
        binding.rvListId.isEdgeItemsCenteringEnabled
        binding.rvListId.layoutManager = LinearLayoutManager(this)

        val menuItems: ArrayList<MenuItem> = ArrayList()
        for (i in 1..5){
            if(i==1)
            menuItems.add(MenuItem( "Measure HR "))
            if(i==2) menuItems.add(MenuItem( "Export HR Data "))
            if(i>2)
            menuItems.add(MenuItem( "Item "+i))

        }

        binding.rvListId.adapter =
            MainMenuAdapter(this, menuItems, object : MainMenuAdapter.AdapterCallback {
                override fun onItemClicked(menuPosition: Int?) {
                    when (menuPosition) {
                        0 -> startActivity(Intent(this@MainActivity,MeasHeartRateActivity::class.java).
                                putExtra(HR_TYPE, FLAG_HR))
                        1 -> startActivity(Intent(this@MainActivity,MeasHeartRateActivity::class.java).
                                putExtra(HR_TYPE, FLAG_EXPORT_HR))
                        else -> Toast.makeText(this@MainActivity,"Item "+menuPosition,Toast.LENGTH_SHORT).show()
                    }
                }
            })

    }

    private fun checkSensorIsAvailable(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (sensorManager?.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
            Toast.makeText(this,"HeartBeat supports",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"no HeartBeat supports",Toast.LENGTH_SHORT).show()
        }
    }
}