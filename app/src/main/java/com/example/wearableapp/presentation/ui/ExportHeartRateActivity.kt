package com.example.wearableapp.presentation.ui

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
import com.example.wearableapp.presentation.utils.Constants
import com.example.wearableapp.presentation.viewmodel.ExportHeartRateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExportHeartRateActivity : AppCompatActivity() {

    val TAG = ExportHeartRateActivity::class.simpleName
    private lateinit var binding: ActivityExportHeartRateBinding
    private val viewModel by viewModel<ExportHeartRateViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExportHeartRateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.tvHeartExConfirmId.setOnClickListener{
            getDataForExport()
        }
    }

    private fun getDataForExport(){
        viewModel.getAllHearRateData().observe(this, Observer {
            if(it.isNotEmpty()) {
                viewModel.heartRateData.value = it
                Log.d(TAG, "Size------" + it.size)
                if (CheckPermission.isPermissionCheck(this)) {
                    exportConfirm()
                    stateExportData()
                } else {
                    Toast.makeText(this, "Please grand storage permission..", Toast.LENGTH_SHORT)
                        .show()
                    CheckPermission.isPermissionCheck(this)
                }
            }
        })

    }

    private fun exportConfirm(){
        binding.clExportHeartConfirmId.visibility = View.GONE
        binding.clExportHeartRateMsgId.visibility = View.VISIBLE
        binding.tvHeartExportUploadingId.visibility = View.VISIBLE
    }

    private fun exportCompleted(){
        binding.tvHeartExportedDataId.visibility = View.VISIBLE
        binding.tvHeartExportUploadingId.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
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

    private fun stateExportData() {
         viewModel.startDataExporting(viewModel.heartRateData.value)
         binding.progressBar.visibility = View.VISIBLE
         viewModel.exportStatus.observe(this, Observer {
             Log.d(TAG,"Status-"+it)
             when(it){
                Constants.Status.STARTED.name ->{
                     binding.progressBar.progress = 10
                     binding.progressBar.isAnimating
                     binding.progressBar.secondaryProgress = 50
                 }
                 Constants.Status.SUCCESSED.name->{
                     exportCompleted()
                 }
                 Constants.Status.FAILED.name->{
                     binding.progressBar.visibility = View.GONE
                     Toast.makeText(this,"Export data failed",Toast.LENGTH_SHORT).show()
                 }
             }
         })
    }
}