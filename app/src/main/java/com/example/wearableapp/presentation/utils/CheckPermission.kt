package com.example.wearableapp.presentation.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class CheckPermission {

    companion object {
        var permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        fun isPermissionCheck(activity: Activity): Boolean {
            var result: Int
            val listPermissionsNeeded: MutableList<String> = ArrayList()
            for (p in permissions) {
                result = ContextCompat.checkSelfPermission(activity, p)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p)
                }
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    activity,
                    listPermissionsNeeded.toTypedArray(),
                    100
                )
                return false
            }
            return true
        }
    }
}