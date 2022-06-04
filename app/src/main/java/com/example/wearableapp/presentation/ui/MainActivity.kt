package com.example.wearableapp.presentation.ui
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wearableapp.databinding.ActivityMainBinding
import com.example.wearableapp.presentation.adapter.MainMenuAdapter
import com.example.wearableapp.presentation.service.HeartRateSensorService
import com.example.wearableapp.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindAdapter()
    }

    private fun bindAdapter(){
        binding.rvListId.hasFixedSize()
        binding.rvListId.isEdgeItemsCenteringEnabled
        binding.rvListId.layoutManager = LinearLayoutManager(this)
        binding.rvListId.adapter =
            MainMenuAdapter(this, viewModel.getMenuList(), object : MainMenuAdapter.AdapterCallback {
                override fun onItemClicked(menuPosition: Int?) {
                    when (menuPosition) {
                        0 -> startActivity(Intent(this@MainActivity,MeasureHeartRateActivity::class.java))
                        1 -> startActivity(Intent(this@MainActivity,ExportHeartRateActivity::class.java))
                        else -> Toast.makeText(this@MainActivity,"Item "+menuPosition,Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }


}