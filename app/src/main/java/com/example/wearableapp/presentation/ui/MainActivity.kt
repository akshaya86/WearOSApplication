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
            menuItems.add(MenuItem( "Measure HR ",R.drawable.ic_android_black_24dp))
            if(i==2) menuItems.add(MenuItem( "Export HR Data ",R.drawable.ic_android_black_24dp))
            if(i>2)
            menuItems.add(MenuItem( "Item "+i,R.drawable.ic_android_black_24dp))

        }

        binding.rvListId.adapter =
            MainMenuAdapter(this, menuItems, object : MainMenuAdapter.AdapterCallback {
                override fun onItemClicked(menuPosition: Int?) {
                    when (menuPosition) {
                        0 -> startActivity(Intent(this@MainActivity,MeasHeartRateActivity::class.java))
                        1 -> startActivity(Intent(this@MainActivity,MeasHeartRateActivity::class.java))
                        2 -> Toast.makeText(this@MainActivity,"2222",Toast.LENGTH_SHORT).show()
                        3 -> Toast.makeText(this@MainActivity,"333",Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this@MainActivity,"else",Toast.LENGTH_SHORT).show()
                    }
                }
            })

        /*binding.btnMeasureHrId.setOnClickListener {
            Toast.makeText(this,"Measure HR",Toast.LENGTH_SHORT).show()
        }
        binding.btnExportHrDataId.setOnClickListener {
            Toast.makeText(this,"Export HR",Toast.LENGTH_SHORT).show()
        }*/

    }

    private fun checkSensorIsAvailable(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (sensorManager?.getDefaultSensor(Sensor.TYPE_HEART_RATE) != null) {
            Toast.makeText(this,"HEART_BEAT supports",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"no HEART_BEAT supports",Toast.LENGTH_SHORT).show()
        }
    }
}