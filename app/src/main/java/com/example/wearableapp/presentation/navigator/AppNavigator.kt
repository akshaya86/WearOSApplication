package com.example.wearableapp.presentation.navigator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.wearableapp.presentation.base.BaseActivity
import java.io.Serializable

class AppNavigator(private val activity: AppCompatActivity) {
  
  private fun navigateTo(intent: Intent) = activity.startActivity(intent)
  
  private inline fun <reified T : BaseActivity> getIntent() = Intent(activity, T::class.java)
}
