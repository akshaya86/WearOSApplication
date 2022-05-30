package com.example.wearableapp.presentation.ui
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wearableapp.R
import com.example.wearableapp.databinding.ActivityMainBinding
import com.example.wearableapp.presentation.utils.Constants.Companion.FLAG_EXPORT_HR
import com.example.wearableapp.presentation.utils.Constants.Companion.FLAG_HR
import com.example.wearableapp.presentation.utils.Constants.Companion.HR_TYPE
import com.example.wearableapp.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    private var sensorManager: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkSensorIsAvailable()
        binding.rvListId.hasFixedSize()
        binding.rvListId.isEdgeItemsCenteringEnabled
        binding.rvListId.layoutManager = LinearLayoutManager(this)



        binding.rvListId.adapter =
            MainMenuAdapter(this, viewModel.getMenuList(), object : MainMenuAdapter.AdapterCallback {
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