package com.example.wearableapp.presentation.ui

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.wearableapp.R
import com.example.wearableapp.databinding.ActivityExportHeartRateBinding
import com.example.wearableapp.presentation.utils.CheckPermission
import com.example.wearableapp.presentation.viewmodel.ExportHeartRateViewModel
import com.example.wearableapp.presentation.viewmodel.MeasureHeartRateViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExportHeartRateActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityExportHeartRateBinding
    private val viewModel by viewModel<ExportHeartRateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExportHeartRateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.tvHeartExConfirmId.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tvHeartExConfirmId -> {
                getDataForExport()
            }
        }
    }

    private fun getDataForExport(){
        viewModel.getAllHearRateData().observe(this, Observer {
            val result = it
            Log.d("Data","Data------"+result[0].name)
            if(CheckPermission.isPermissionCheck(this)){
                exportConfirm()
                stateExportData()
            }else{
                Toast.makeText(this,"Please grand storage permission..", Toast.LENGTH_SHORT).show()
                CheckPermission.isPermissionCheck(this)
            }
        })

    }

    private fun exportConfirm(){
        binding.clExportHeartConfirmId.visibility = View.GONE
        binding.clExportHeartRateMsgId.visibility = View.VISIBLE
        binding.tvHeartExportUploadingId.visibility = View.VISIBLE
        //temp added
        GlobalScope.launch {viewModel.insertData()}
    }

    private fun exportCompleted(){
        binding.tvHeartExportedDataId.visibility = View.VISIBLE
        binding.tvHeartExportUploadingId.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==100){
            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                exportConfirm()
                stateExportData()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun stateExportData(){
        viewModel.startDataExporting()
    }
}