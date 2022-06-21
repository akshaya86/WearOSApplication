package com.example.wearableapp.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import com.example.wearableapp.R
import com.example.wearableapp.databinding.ActivityMainBinding
import com.example.wearableapp.presentation.adapter.MainMenuAdapter
import com.example.wearableapp.presentation.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModel<MainViewModel>()

    var snapHelper =  LinearSnapHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindAdapter()
    }

    private fun bindAdapter(){
        binding.rvListId.hasFixedSize()
        binding.rvListId.isEdgeItemsCenteringEnabled
        binding.rvListId.layoutManager = WearableLinearLayoutManager(this,object :
            WearableLinearLayoutManager.LayoutCallback(){
            override fun onLayoutFinished(child: View?, parent: RecyclerView?) {
               getCentralViewPosition(child,parent)
            }

        })

        binding.rvListId.adapter =
            MainMenuAdapter(this, viewModel.getMenuList(), object : MainMenuAdapter.AdapterCallback {
                override fun onItemClicked(menuPosition: Int?) {
                    when (menuPosition) {
                        0 -> startActivity(Intent(this@MainActivity,MeasureHeartRateActivity::class.java))
                        1 -> startActivity(Intent(this@MainActivity,ExportHeartRateActivity::class.java))
                        else-> Log.d("TAG","ACTION ITEM 3")
                    }
                }
            })

        snapHelper.attachToRecyclerView(binding.rvListId)
    }


    private fun getCentralViewPosition(child: View?, parent: RecyclerView?) {
            val centerOffset = child?.height?.toFloat()!! / 2.0f / parent?.height!!.toFloat()
            val yRelativeToCenterOffset = child.y / parent.height + centerOffset
            var progressToCenter = abs(0.5f - yRelativeToCenterOffset)
            progressToCenter = progressToCenter.coerceAtMost(0.45f)
            val centerPosition = 1 - progressToCenter
            if(centerPosition>=0.9){
                child.background = getDrawable(R.drawable.item_highlighter)
            }else{
                child.background = getDrawable(R.drawable.item_selector)
            }
    }


}